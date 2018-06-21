# 仿微博，微信，发送图片，发视频动态

演示效果
  ==
  ![效果视频](https://github.com/oyd5201/Micro-blog-we_chat-dynamic-circle-of-friends-release/raw/master/textCirle/image/video1.mp4)
  
  ![效果图1](https://github.com/oyd5201/Micro-blog-we_chat-dynamic-circle-of-friends-release/raw/master/textCirle/image/xg1.jpg)
  ![效果图2](https://github.com/oyd5201/Micro-blog-we_chat-dynamic-circle-of-friends-release/raw/master/textCirle/image/xg2.jpg)
  

功能特点
==
*1.适配android6.0+系统

*2.解决部分机型裁剪闪退问题

*3.解决图片过大oom闪退问题

*4.动态获取系统权限，避免闪退

*5.支持相片or视频的单选和多选

*6.支持裁剪比例设置，如常用的 1:1、3：4、3:2、16:9 默认为图片大小

*7.支持视频预览

*8.支持gif图片

*9.支持.webp格式图片

*10.支持一些常用场景设置：如:是否裁剪、是否预览图片、是否显示相机等

*11.新增自定义主题设置

*12.新增图片勾选样式设置

*13.新增图片裁剪宽高设置

*14.新增图片压缩处理

*15.新增录视频最大时间设置

*16.新增视频清晰度设置

*17.新增QQ选择风格，带数字效果

*18.新增自定义 文字颜色 背景色让风格和项目更搭配

*19.新增多图裁剪功能

*20.新增LuBan多图压缩

*21.新增单独拍照功能

*22.新增压缩大小设置

*23.新增Luban压缩档次设置

*24.新增圆形头像裁剪

*25.新增音频功能查询

重要的事情说三遍记得添加权限
==

  		<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
  		<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
  		<uses-permission android:name="android.permission.CAMERA" />
  
  集成方式
  ==
  
  方式一 compile引入
--

		dependencies {
   		 implementation 'com.github.LuckSiege.PictureSelector:picture_library:v2.2.3'
		}

项目根目录build.gradle加入
--

		allprojects {
  		 repositories {
   		   jcenter()
     		 maven { url 'https://jitpack.io' }
 		  }
		}
方式二 maven引入
--

step 1.

		<repositories>
     		  <repository>
      		 <id>jitpack.io</id>
			<url>https://jitpack.io</url>
      		 </repository>
 		</repositories>
		
step 2.


		<dependency>
   		   <groupId>com.github.LuckSiege.PictureSelector</groupId>
     		 <artifactId>picture_library</artifactId>
     		 <version>v2.2.3</version> 
		</dependency>
  
  常见错误
  ==
  
 		重要：PictureSelector.create()；调用此方法时，在activity中传activity.this，在fragment中请传fragment.this,
 		影响回调到哪个地方的onActivityResult()。
 
		 问题一：
		 rxjava冲突：在app build.gradle下添加
		 packagingOptions {
 		  exclude 'META-INF/rxjava.properties'
		 }  
 
		 问题二：
		 java.lang.NullPointerException: 
		 Attempt to invoke virtual method 'android.content.res.XmlResourceParser 
 		android.content.pm.ProviderInfo.loadXmlMetaData(android.content.pm.PackageManager, java.lang.String)'
 		on a null object reference
 
 * 注意 从v2.1.3版本中，将不需要配制以下内容
 
 application下添加如下节点:
 
 <provider
      android:name="android.support.v4.content.FileProvider"
      android:authorities="${applicationId}.provider"
      android:exported="false"
      android:grantUriPermissions="true">
       <meta-data
         android:name="android.support.FILE_PROVIDER_PATHS"
         android:resource="@xml/file_paths" />
</provider>



		问题三：
		经测试在小米部分低端机中，Fragment调用PictureSelector 2.0 拍照有时内存不足会暂时回收activity,
		导致其fragment会重新创建 建议在fragment所依赖的activity加上如下代码:
		if (savedInstanceState == null) {
		      // 添加显示第一个fragment
			fragment = new PhotoFragment();
				getSupportFragmentManager().beginTransaction().add(R.id.tab_content, fragment,
				    PictureConfig.FC_TAG).show(fragment)
				    .commit();
		     } else { 
			fragment = (PhotoFragment) getSupportFragmentManager()
			  .findFragmentByTag(PictureConfig.FC_TAG);
		}
		这里就是如果是被回收时，则不重新创建 通过tag取出fragment的实例。

		问题四：
		glide冲突
		由于PictureSelector 2.0引入的是最新的glide 4.5.0,所以将项目中老版本的glide删除,并且将报错代码换成如下写法：
		RequestOptions options = new RequestOptions();
		options.placeholder(R.drawable.image);
		Glide.with(context).load(url).apply(options).into(imageView);


核心代码
==
// 进入相册 以下是例子：用不到的api可以不写
		 PictureSelector.create(MainActivity.this)
			.openGallery()//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
			.theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
			.maxSelectNum()// 最大图片选择数量 int
			.minSelectNum()// 最小选择数量 int
			.imageSpanCount(4)// 每行显示个数 int
			.selectionMode()// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
			.previewImage()// 是否可预览图片 true or false
			.previewVideo()// 是否可预览视频 true or false
			.enablePreviewAudio() // 是否可播放音频 true or false
			.isCamera()// 是否显示拍照按钮 true or false
			.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
			.isZoomAnim(true)// 图片列表点击 缩放效果 默认true
			.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
			.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
			.enableCrop()// 是否裁剪 true or false
			.compress()// 是否压缩 true or false
			.glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
			.withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
			.hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
			.isGif()// 是否显示gif图片 true or false
			.compressSavePath(getPath())//压缩图片保存地址
			.freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
			.circleDimmedLayer()// 是否圆形裁剪 true or false
			.showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
			.showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
			.openClickSound()// 是否开启点击声音 true or false
			.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
			.previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
			.cropCompressQuality()// 裁剪压缩质量 默认90 int
			.minimumCompressSize(100)// 小于100kb的图片不压缩 
			.synOrAsy(true)//同步true或异步false 压缩 默认同步
			.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int 
			.rotateEnabled() // 裁剪是否可旋转图片 true or false
			.scaleEnabled()// 裁剪是否可放大缩小图片 true or false
			.videoQuality()// 视频录制质量 0 or 1 int
			.videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int 
			.videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int 
			.recordVideoSecond()//视频秒数录制 默认60s int
			.isDragFrame(false)// 是否可拖动裁剪框(固定)
			.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code    
  
  
  
  
