# WanAndroidKMP

## 一 项目预览

Android截图

| ![][kmpwaz-az-1] | ![][kmpwaz-az-2] | ![][kmpwaz-az-3] |
| :--------------: | :--------------: | :--------------: |
| ![][kmpwaz-az-4] | ![][kmpwaz-az-5] | ![][kmpwaz-az-6] |
| ![][kmpwaz-az-7] | ![][kmpwaz-az-8] | ![][kmpwaz-az-9] |

IOS截图

| ![][kmpwaz-ios-1] | ![][kmpwaz-ios-2] | ![][kmpwaz-ios-3] |
| :---------------: | :---------------: | :---------------: |
| ![][kmpwaz-ios-4] | ![][kmpwaz-ios-5] | ![][kmpwaz-ios-6] |
| ![][kmpwaz-ios-7] | ![][kmpwaz-ios-8] | ![][kmpwaz-ios-9] |

## 二 开发环境

* 软件系统：MacOS 13.5
* 开发工具：Android Studio Giraffe|2022.3.1
* JRE：18.0.1

## 三 项目创建(基于模版)

### 3.1 模版

* [compose-multiplatform-template](https://github.com/JetBrains/compose-multiplatform-template#readme)

### 3.2 模版修改

#### 项目名称(settings.gradles.kts)

* rootProject.name

#### androidApp(build.gradle.kts)

* namespace：设置包名
* applicationId
* src/androidMain/kotlin下面的包名与namespace一样

#### iosApp

```
kdoctor --team-ids
```

执行上面的指令，获取Apple Team ID

iosApp/Configuration/Config.xcconfig配置以下信息

* TEAM_ID：Apple Team ID
*  BUNDLE_ID：包名
* APP_NAME：项目名称

#### desktopApp(build.gradle.kts)

* packageName：应用名称
* packageVersion：版本

## 四 版本

### v1.0

* 基于模版[compose-multiplatform-template](https://github.com/JetBrains/compose-multiplatform-template#readme)创建项目

### v2.0

添加依赖：

* compose.materialIconsExtended：Icon扩展
* compose.animation：动画
* voyager-navigator：voyager库
* voyager-transitions：voyager库

内容修改：

* desktopApp，通过Window.title修改标题显示
* 主题设置
* 项目框架搭建(首页、导航、项目、消息、我的)

### v3.0

#### 依赖

添加依赖-commonMain：

* kamel-image：网络图片
* ktor-client-core：ktor网络请求
* ktor-server-auto-head-response：ktor网络请求
* ktor-client-logging：ktor日志打印
* ktor-serialization-kotlinx-json：ktor序列化
* ktor-client-content-negotiation：ktor序列化/反序列化特定格式的内容
* voyager-navigator：voyager导航
* voyager-transitions：voyager专场
* xxfast:kstore-file：kstore文件
* kstore：kstore数据保存

添加依赖-androidMain：

* ktor-client-android：ktor网络安卓端
* accompanist-systemuicontroller：systemuicontroller导航

添加依赖-iosMain：

* ktor-client-darwin：ktor网络ios端

添加依赖-desktopApp

* appdirs：使用kstore-file时，需要用到

#### UI界面

* 首页
* 导航
* 项目
* 消息
* 我的
* 消息

### v4.0 项目名称修改

* WanAndroid-Compose-Multiplatform改为WanAndroidKMP

### v5.0 项目打包

* Android：生成签名文件据此生成abi对应输出apk
* windows：输出exe文件安装后创建桌面快捷方式
* iOS：暂无
* Linux：暂无
* mac：暂无


<!--WanAndroid-az-->
[kmpwaz-az-1]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-az-home-1.png
[kmpwaz-az-2]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-az-navigator-2.png
[kmpwaz-az-3]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-az-project-3.png
[kmpwaz-az-4]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-az-msg-4.png
[kmpwaz-az-5]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-az-msg-5.png
[kmpwaz-az-6]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-az-me-6.png
[kmpwaz-az-7]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-az-me-7.png
[kmpwaz-az-8]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-az-login-8.png
[kmpwaz-az-9]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-az-register-9.png
<!--WanAndroid-ios-->
[kmpwaz-ios-1]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-ios-home-1.png
[kmpwaz-ios-2]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-ios-navigator-2.png
[kmpwaz-ios-3]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-ios-project-3.png
[kmpwaz-ios-4]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-ios-msg-4.png
[kmpwaz-ios-5]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-ios-msg-5.png
[kmpwaz-ios-6]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-ios-me-6.png
[kmpwaz-ios-7]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-ios-me-7.png
[kmpwaz-ios-8]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-ios-login-8.png
[kmpwaz-ios-9]:https://cdn.jsdelivr.net/gh/PGzxc/CDN/blog-resume/kmpwaz-ios-register-9.png