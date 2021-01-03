# 葫芦娃大战妖精

### 作者与分工

**181860026 韩进**

负责框架的设计，数据通信与同步，战斗逻辑的实现，回放功能设计

**181860067 努吉别克**

负责所有javafx界面的设计，各种图片资源素材的制作与搜索，component模块中的葫芦娃类、子弹类的的编写

### 游戏介绍

本游戏为双人实时对战游戏，双方操作各自阵营的葫芦娃或妖精，在固定图格上移动进行战斗，直到一方阵营全部死亡后游戏结束。

战斗开始界面

![开始界面](https://github.com/nwod-edispu/img_storage/blob/master/gourd_war/start.jpg)

登录界面

这里可以选择通过server或者client登录，同时可以选择通过游客身份或者账号密码登录，如果没有账号密码可以选择注册按钮进行注册（若本地数据库没有配置请务必选择Tourist登录！）

![登录界面](https://github.com/nwod-edispu/img_storage/blob/master/gourd_war/server.png)

如果选择了以server登录，则会进入地图选择界面

![地图](https://github.com/nwod-edispu/img_storage/blob/master/gourd_war/map.jpg)

选择地图后会进入等待连接界面

![server等待连接](https://github.com/nwod-edispu/img_storage/blob/master/gourd_war/serverwaiting.png)

如果选择以client登录，则会要求输入server的ip地址

![客户端](https://github.com/nwod-edispu/img_storage/blob/master/gourd_war/client.png)

连接成功后会进入战斗界面，下面是部分战斗的展示

![battle1](https://github.com/nwod-edispu/img_storage/blob/master/gourd_war/battlegif1.gif)

![battle2](https://github.com/nwod-edispu/img_storage/blob/master/gourd_war/battlegif2.gif)

![battle3](https://github.com/nwod-edispu/img_storage/blob/master/gourd_war/battlegif3.gif)

战斗结束后

![战斗结束](https://github.com/nwod-edispu/img_storage/blob/master/gourd_war/finish.jpg)

在开始界面或者结束界面按下L或者直接点击回放游戏按钮选择回放文件即可播放，然后选择游戏目录下的record文件夹中的文件即可，记录文件的命名为当时的游戏时间组成的字符串。

![回放](https://github.com/nwod-edispu/img_storage/blob/master/gourd_war/record.png)

### 模块介绍

在constant模块中，以一个Constant类集成了所有的常量对象，便于其他模块对其进行引用；在map模块中，包含了所有的静态场景；在battle模块中，包含了战斗场景的所有逻辑，以及连接通信模块；在component模块中，包含了生物，子弹，图格这些组件。由一个SceneSwitch(场景转换器)类协调各场景的切换。

#### component模块

##### 生物（Creature类）

葫芦娃与妖精统一用一个生物类进行表示，唯一的区分是属性中传入`isGood`参数进行表征。生物具有生命值，攻击力，子弹类型，攻击范围，当前网格，图像的属性，以及一条代表血条的长方形框，会根据血量的实时变化来调整长方形的边长来进行绘制。

##### 子弹（Bullet类）

子弹包括坐标，伤害，方向，子弹类型，范围的属性，为了产生爆炸的特效，通过子弹类型内置了每一帧的爆炸效果图，在子弹类内部设置了`clock`来进行动画的播放。

##### 网格（GridMap类）

`GridMap`类是一个工具类，初始化时设置了每行每列的网格个数，单个网格的长宽，左上角网格坐标。类中集成了大量函数，包括将坐标转换为网格id，将网格id转换为生物站位坐标，将网格id转换为子弹坐标，判断一个网格是否在另一个网格的范围内，得到一个网格的所有相邻网格列表，通过源网格与目标网格id判断方向。

#### constant模块

包含了一个Constant类，初始化了包括默认端口号，子弹图片，战场图片等一系列的静态常量，方便对其统一调用。

#### battle模块

##### 数据读入

每一个葫芦娃以及妖精对象有大量的属性，包含名字，生命，攻击力，图片，位置等，因此使用json文件对其进行统一的初始化。这里使用apache的common-io的`FileUtils`进行文件的读入，并将其转换为一个`JsonObject`来对所有的生物对象进行初始化。

##### 通信（Connect类，BattleMsg类）

进入游戏后，由一台主机开启一个线程作为服务器等待连接，当另一台主机启动时，开启另一个客户端线程进行连接。当连接通过后通过回调函数进行界面的切换。

由于服务器与客户端之间需要同步状态，因此需要实时把战斗中的状态改变通过通信进行协调。经过合理设计，所有的战场状态改变可以囊括为生物的移动，生物产生的攻击，攻击造成的伤害。由于生物产生的攻击有近程和远程，这里进行一个统一，将近程攻击看作一个距离为0的远程攻击，那么所有攻击可以用一个子弹来描述。因此将通信协议设计为一个`MoveMsg`,`BulletMsg`,`DamageMsg`，这三者都继承自一个`BattleMsg`的基类，以便于通过多态进行操作。

`MoveMsg`需要的参数为源网格srcId与目的网格dstId，表示从网格srcId移动到dstId.

```java
MoveMsg(int srcId, int dstId, boolean isServer, long clock)
{
	super(MsgType.MOVE_MSG, isServer, clock);
	this.srcId = srcId;
	this.dstId = dstId;
}
```

`BulletMsg`需要的是也是源网格srcId与目的网格dstId，表示的是子弹产生位置为srcId代表的网格，dstId代表的网格是子弹的最远距离。

```java
BulletMsg(int srcId, int dstId, boolean isServer, long clock)
{
    super(MsgType.BULLET_MSG, isServer, clock);
    this.srcId = srcId;
    this.dstId = dstId;
}
```

`DamageMsg`需要的是目标的名字与伤害值，这里不能用网格id来代替，因为造成伤害的那一刻该网格上还有生物，但是下一刻该网格的生物移动了，这个伤害便无法生效了。

```java

DamageMsg(String name, int damage, boolean isServer, long clock)
{
    super(MsgType.DAMAGE_MSG, isServer, clock);
    this.damage = damage;
    this.name = name;
}
```

为了将`BattleMsg`进行传输，通过序列化接口`Serializable`将对象序列化为二进制流，通过网络进行传输。

##### 资源管理（ResourceManager类）

由于葫芦娃与妖精方是并发执行，所以有可能同时发起移动到同一个单元格的请求。为了维护单元格这个资源，建立一个`ResourceManager`类，其中记录了所有图格资源的归属。当收到一条`MoveMsg`时，先判断所要移动去的图格是否被占用，如果没有，则将该图格标记为占用，并且允许该条`MoveMsg`。如果此时图格已经被占用，这该条`MoveMsg`无效。

##### 回放（Recorder类）

由于战斗是由`BattleMsg`组织而成，因此只要记录何时发生了哪个`BattleMsg`便能恢复整个战斗。因此在战斗过程中，需要将所有的`BattleMsg`输出到一个文件中进行存档。为了自动产生一个不重复的文件名，通过当前的时间生成一个字符串作为文件名，然后将`BattleMsg`序列化后输出至文件。但是`ObjectOutputStream`会将打开的文件自动切割为四个字节，因此需要注意在一次战斗中只将该输出流打开一次，否则会造成信息的缺失。

##### 战场控制（BattleField类）

以葫芦娃为例，因为所有葫芦娃只能在网格上行动，因此申请一个大小为网格总数的`calabashBrothers`的数组。将所有网格从左到右，从上到下从0开始编号，如果编号为id的网格上有葫芦娃，则`calabashBrothers[id]`上放置一个葫芦娃对象，否则置为`null`。那么所有对生物的操作可以通过网格间接操作，当需要移动生物时，可以通过点击的网格上是否有生物，以及所要移动到的网格是否为空闲；当需要发射子弹时，只要给出开始网格的id以及方向和最大距离即可；当子弹造成伤害时，只要给出目标网格id上的生物姓名，以及伤害值即可。这三种刚好对应为`MoveMsg`,`BulletMsg`,`DamageMsg`.

#### map模块

图形界面部分主要使用了JavaFX库，借助Scene Builder设计界面、结合CSS样式装饰控件，综合利用了JavaFX库中提供的各动画类、媒体类、图像处理相关类丰富界面。

`StartController`为游戏开始界面，界面设计由`start.fxml`描述，其中控件样式由`start.css`描述，为控件添加了视觉特效等。`StartController`类中定义了各个按钮鼠标事件处理方法即切换到新的界面。

`SignUpController`类绑定登录用户界面。界面设计由`login.fxml`描述，控件样式由`login.css`描述。`SignUpController`类中定义了各个控件事件，也为静态图像添加了鼠标事件处理方法。登录界面SERVER、CLIENT供用户选择是服务器端还是客户端也可以选择TOURIST即游客模式省略匹配用户信息过程。若不选择CLIENT按钮则将会根据用户身份以及输入的用户信息匹配数据库中的数据，匹配成功则开始游戏、否则会有相应提示信息。

`SignUpController`类绑定用户注册界面，界面设计由`signup.fxml`描述。用户可以注册账号，新的账号的密码信息将会存入数据库中，用于登录时匹配。

`ServerWaitingController`类定义了等待客户连接界面,主要利用了面板。

`MapChange`类定义了地图选择界面。利用了`AnchorPane`、`HBox`布局类、`Button`控件、相机视野、以及动画效果。

`FinishController`类定义了游戏结束界面。主要利用了并行动画、串行动画、路径动画。

`Database`类主要用于加载驱动与连接数据库。需要在`getDbconnection`方法中配置数据库用户名和密码，在`LoginController`类的`isValidLogin`方法中检测用户输入的信息。

#### 测试

通过JUnit单元测试对包括数据初始化，各种图格操作，回放文件记录的正确性，资源同步锁定的正确性进行了测试，其余的javafx界面的内容直接通过用户的交互手动测试。





