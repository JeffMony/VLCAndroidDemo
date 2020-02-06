# VLCAndroidDemo

### 官方库
maven库地址：[https://dl.bintray.com/videolan/Android/](https://dl.bintray.com/videolan/Android/)
<br/>项目使用的是 3.2.4 版本：[https://dl.bintray.com/videolan/Android/org/videolan/android/libvlc-all/3.2.4/](https://dl.bintray.com/videolan/Android/org/videolan/android/libvlc-all/3.2.4/)
<br/>官方wiki地址：[https://wiki.videolan.org/](https://wiki.videolan.org/)

### VLC调用步骤
> * 初始化 LibVLC 对象;
> * 初始化 org.videolan.libvlc.MediaPlayer 对象;
> * 获取 IVLCVout 对象;
> * 将SurfaceView 或者 TextureView 设置到IVLCVout 对象中;
> * 构建 org.videolan.libvlc.Media 对象，将url 设置好;
> * 调用 MediaPlayer.play 播放视频
> * 设置 IVLCVout.OnNewVideoLayoutListener 监听接口，监听onNewVideoLayout 回调获取 video size 具体数据;


