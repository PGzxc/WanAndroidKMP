# WanAndroidKMP

## 一 项目预览


Android截图

| ![][multi-az-waz-1] | ![][multi-az-waz-2] | ![][multi-az-waz-3] | ![][multi-az-waz-4] |
| ------------------- | ------------------- | ------------------- | ------------------- |
| ![][multi-az-waz-5] | ![][multi-az-waz-6] | ![][multi-az-waz-7] | ![][multi-az-waz-8] |
| ![][multi-az-waz-9] |                     |                     |                     |

IOS截图

| ![][multi-ios-waz-1] | ![][multi-ios-waz-2] | ![][multi-ios-waz-3] | ![][multi-ios-waz-4] |
| -------------------- | -------------------- | -------------------- | -------------------- |
| ![][multi-ios-waz-5] | ![][multi-ios-waz-6] | ![][multi-ios-waz-7] | ![][multi-ios-waz-8] |
| ![][multi-ios-waz-9] |                      |                      |                      |

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

<!--az-->
[multi-az-waz-1]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-az-waz-home-1.png
[multi-az-waz-2]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-az-waz-navigator-2.png
[multi-az-waz-3]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-az-waz-project-3.png
[multi-az-waz-4]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-az-waz-msg-4.png
[multi-az-waz-5]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-az-waz-msg-5.png
[multi-az-waz-6]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-az-waz-me-6.png
[multi-az-waz-7]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-az-waz-me-7.png
[multi-az-waz-8]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-az-waz-login-8.png
[multi-az-waz-9]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-az-waz-register-9.png
<!--ios-->
[multi-ios-waz-1]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-ios-waz-home-1.png
[multi-ios-waz-2]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-ios-waz-navigator-2.png
[multi-ios-waz-3]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-ios-waz-project-3.png
[multi-ios-waz-4]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-ios-waz-msg-4.png
[multi-ios-waz-5]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-ios-waz-msg-5.png
[multi-ios-waz-6]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-ios-waz-me-6.png
[multi-ios-waz-7]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-ios-waz-me-7.png
[multi-ios-waz-8]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-ios-waz-login-8.png
[multi-ios-waz-9]:https://cdn.staticaly.com/gh/PGzxc/CDN/master/blog-resume/multiplatform-ios-waz-register-9.png