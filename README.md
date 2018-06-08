<h2>Android车牌识别 </h2>
<h3> 一、下载</h3>
<h5> 1.tess-two源码  </h5>  
[下载链接](https://github.com/gaojulong/tess-two)   
<h5>2.tessdata语言数据文件  </h5>  
[下载链接](https://github.com/tesseract-ocr/tessdata)   
咱们需要的只是识别车牌，所以英文的语言数据eng.traineddata就够了。  

![image.png](https://upload-images.jianshu.io/upload_images/6540285-17f88187f0962173.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  

<h3> 二、ndk编译tess-two</h3>
 1.下载好tess-two源文件后，终端进入tess-two文件夹  
 
![image.png](https://upload-images.jianshu.io/upload_images/6540285-22497920dd29be16.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  

  2.编译生成.so文件，过程可能有点慢大概20分钟,如果没有安装ndk请参考这里   
  [<查看MacOS ndk配置>](https://www.jianshu.com/p/fecbf2204046)
  
  	ndk-build    
  	
![image.png](https://upload-images.jianshu.io/upload_images/6540285-aacf5484d8686ddc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
3.查看tess-two里的libs文件，里面就是我们所需要的不同框架so文件

![image.png](https://upload-images.jianshu.io/upload_images/6540285-590eedab9af4acf9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

<h3> 三、项目搭建 </h3>
 1.新建一个空项目，把生成的so文件放入到lisb文件里
 
 ![image.png](https://upload-images.jianshu.io/upload_images/6540285-1e3ee0cdf8bb4d73.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
 2.找到源文件tess-two里src文件里的com文件，复制到项目里
  
 ![image.png](https://upload-images.jianshu.io/upload_images/6540285-0a445cf52c1becd3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
   
 3.代码其实很简单，如果只用于车牌识别有一个技巧，就是设置白名单和黑名单   

	TessBaseAPI baseApi = new TessBaseAPI();  
 	baseApi.init(getSDPath(), language);//设置语言和获取路径
 	baseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"); // 识别白名单
    baseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-[]}{;:'\"\\|~`,./<>?"); // 识别黑名单
4.配置build.gradle
	
	android {
		ndk {
	            abiFilters "armeabi-v7a", "x86", "armeabi"
	        }
	
	        sourceSets { main { jniLibs.srcDirs = ['libs']
	        }}
	   }
	   
5.最后别忘记添加读写权限
 
 	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
	   

![image.png](https://upload-images.jianshu.io/upload_images/6540285-3d6ea971eec1c50c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
 
 
 <h3> 四、把训练语言数据放入到根目录</h3>
 1.在根目录下创建一个文件夹名字必须为tessdata(必须根目录和tessdata命名)  
 2.把 eng.traineddata放入tessdata里  
 
 ![image.png](https://upload-images.jianshu.io/upload_images/6540285-f7be91590966b9b2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
 
 <h4>    
 总结： 
 介绍的可能有点粗糙，源码放在了github上，有不清楚的地方可以留言，此项目对于生活中车牌识别成功率很低，后边文章会介绍和OpenCV一起使用。先用OpenCV处理二值化后再进行识别！
 
 </h4>

 


