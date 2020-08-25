它来了，它来了，虽说是项目建立了比较长的一段时间，但是真正开始做用了2周。接触`Kotlin`也算是有一段时间了，但是都没有利用`Kotlin`输出点什么东西来，于是就有了此项目《玩安卓Kotlin版本的`Wandroid`》，当做学习`Kotlin`的练手项目。



部分参考 [wanandroid](https://github.com/xiaoyanger0825/wanandroid) ，感谢。基本上都是自己瞎设计的。项目采用Kotlin语言，使用`ViewModel` + `LiveData` + `协程`等。同时也非常感谢API提供者鸿洋大神。



### 已有功能

- Banner、热门文章
- 广场，可查看文章作者分享的所有文章
- 公众号
- 每日问答
- 查看待办、新增待办、修改待办、删除待办

- 项目
- 体系，可根据体系大分类定位到具体位置
- 导航，可根据导航大分类定位到具体位置
- 登录、注册
- 我的积分、我的排行、我的收藏、我的分享



### 预览

| ![Screenshot_20200823_133127_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133127_com.cyl.wandroid.jpg) | ![Screenshot_20200823_133140_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133140_com.cyl.wandroid.jpg) | ![Screenshot_20200823_133151_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133151_com.cyl.wandroid.jpg) |
| :----------------------------------------------------------: | ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![Screenshot_20200823_133236_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133236_com.cyl.wandroid.jpg) | ![Screenshot_20200823_133528_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133528_com.cyl.wandroid.jpg) | ![Screenshot_20200823_133538_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133538_com.cyl.wandroid.jpg) |
| ![Screenshot_20200823_133545_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133545_com.cyl.wandroid.jpg) | ![Screenshot_20200823_133550_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133550_com.cyl.wandroid.jpg) | ![Screenshot_20200823_133554_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133554_com.cyl.wandroid.jpg) |
| ![Screenshot_20200823_133559_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133559_com.cyl.wandroid.jpg) | ![Screenshot_20200823_133612_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133612_com.cyl.wandroid.jpg) | ![Screenshot_20200823_133626_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133626_com.cyl.wandroid.jpg) |
| ![Screenshot_20200823_133633_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133633_com.cyl.wandroid.jpg) | ![Screenshot_20200823_133700_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133700_com.cyl.wandroid.jpg) | ![Screenshot_20200823_133712_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133712_com.cyl.wandroid.jpg) |
| ![Screenshot_20200823_133715_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133715_com.cyl.wandroid.jpg) | ![Screenshot_20200823_133533_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_133533_com.cyl.wandroid.jpg) | ![Screenshot_20200823_135232_com.cyl.wandroid](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/Screenshot_20200823_135232_com.cyl.wandroid.jpg) |





### 项目结构

整个项目并不是根据功能点来划分的，而是根据类的相关性来进行分组。比如所有的`activity`都在`ui/activity`包下。

![image-20200825153428110](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/image-20200825153428110.png)



`base`包下有几个相似的类：

- `BaseActivity`：基类，封装了`Toolbar`相关的逻辑，在`activity`没有任何逻辑操作时继承；
- `BaseViewMiodelActivity`：继承自`BaseActivity`，持有`ViewModel`的对象，在有逻辑操作时继承，此类中获取`ViewModel`的对象供子类使用；
- `BaseRecyclerViewModelActivity`：继承自`BaseViewModelActivity`，同样持有`ViewModel`的对象，不同的是此类是在界面中有`RecyclerView`时继承。该类封装了`LiveData`相关的逻辑、下拉刷新/上拉加载、收藏、点击跳转到网页等。

`BaseFragment`、`BaseViewModelFragment`、`BaseRecyclerViewFragment`同理。

### Apk下载体验

[下载链接](https://www.ncfwwl.com/b/fBSLQ1)



二维码下载

![qrcode](https://github.com/SmartCyl/Wandroid-kotlin/blob/master/images/qrcode.png)

### 更新日志

#### v1.0.0  2020-08-25

- 第一个有基本功能的版本

