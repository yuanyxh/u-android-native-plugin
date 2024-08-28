# u-android-native-plugin

uniapp Android 原生插件, 扩展 uniapp Android App 端能力。

## module

### CameraX

仿微信应用内相机, 主要目的是解决 uniapp 项目在部分机型中, 调用系统拍摄功能闪退问题，见 [应用闪退分析与 uniapp 安卓原生插件开发]。

![cameraX gif](https://qkc148.bvimg.com/18470/a7b0db6a8f7131b6.gif)

添加依赖：

Android Studio 导入此项目中的 `camera` 模块：

![20240108002914](http://qkc148.bvimg.com/18470/30696d04613d3420.png)

![20240108002953](http://qkc148.bvimg.com/18470/3f9ec7c9da1062a6.png)

主项目中的 `build.gradle` 文件中添加 `camera` 模块依赖：

![20240108003108](http://qkc148.bvimg.com/18470/24e407ae40d12af4.png)

使用方式：

```js
const cameraX = uni.requireNativePlugin('CameraX');

cameraX.capture(
  /**
   * emojiPath: 图片编辑时, 支持添加图片, 此参数是本地图片目录的路径。
   * 注意：如果传入非应用私有目录, 需要确保应用具有文件读写的权限。
   * */
  { emojiPath: '/storage/emulated/0/Android/data/com.android.simple/apps/__UNI__7DCA9F7/doc/uniapp_save/' },
  /**
   * 拍摄完成时的回调, imageFilePath 为图片路径。
   * 注意：图片保存在 uniapp 官方推荐的路径下, 此路径下的文件有效期只在此次应用生命周期内, 重启应用文件会默认被删除。
  */
  (data) => console.log(data.imageFilePath);
);
```

***此模块未经过多机型测试，且代码对于很多边界情况没有做处理，建议不要在实际项目中使用***。

### NotifyModule

通知模块，使用 `Toast` 模拟系统自带通知，可以做到在应用外弹出提示的效果。

![notify](http://qkc148.bvimg.com/18470/932cf2370da9d520.gif)

引入方式与 `CameraX` 类似。

使用方式：

```js
const notifyModule = uni.requireNativePlugin('NotifyModule');
/**
 * transmitNotify: 发送一个通知
*/
notifyModule.transmitNotify();
```

***此模式只是测试使用，只可作为一个思路***

[应用闪退分析与 uniapp 安卓原生插件开发]: https://juejin.cn/post/7308219746830630938