# <img src="https://raw.githubusercontent.com/tobsef/XSwing/main/.idea/icon.png" width="38"/> XSwing Plus

> Open Source remake of a classic match 3 arcade game.

[![gameplay](https://raw.githubusercontent.com/tobsef/XSwing/media/gameplay.gif)](https://youtu.be/cLm7sDkO8Z8)  
[![Youtube](https://img.shields.io/badge/Play-white.svg?style=flat&logo=youtube&logoColor=FF0000)](https://youtu.be/cLm7sDkO8Z8)

## 💡 Trivia
The original game was named [Swing Plus -Total Mind Control](https://en.wikipedia.org/wiki/Swing_(video_game)) (Marble Master)
and was developed by [Software 2000](https://en.wikipedia.org/wiki/Software_2000)
a German game developer and publisher. It was released in 1997 for PC, PlayStation and Game Boy Color.

My goal was to bring this masterpiece of arcade action back to life. I wanted to recreate the original
graphics and game mechanics as accurately as possible. The game is written in Java and runs
on Windows, Linux, Mac and Android.

## ⭐ Features
⭐ Spiffy addictive gameplay  
⭐ Hot Seat multiplayer mode  
⭐ 45 Challenging Levels  
⭐ Offline and online highscore  
⭐ Particle and sound effects  
⭐ Multilingual (German and English)  

## 💿 Downloads

[<img alt="Download Windows" height="45px" src="https://raw.githubusercontent.com/tobsef/XSwing/media/download-win.svg"/>](https://dl.xswing.net/XSwing_Plus_windows-x64.exe)  
Windows Setup `XSwing_Plus_windows-x64.exe` `82MB`

[<img alt="Download Mac" height="45px" src="https://raw.githubusercontent.com/tobsef/XSwing/media/download-mac.svg"/>](https://dl.xswing.net/XSwing_Plus_unix.sh)  
Linux Setup `XSwing_Plus_unix.sh` `88MB`

[<img alt="Download Linux" height="45px" src="https://raw.githubusercontent.com/tobsef/XSwing/media/download-linux.svg"/>](https://dl.xswing.net/XSwing_Plus_macos.dmg)  
Mac Setup `XSwing_Plus_macos.dmg` `83MB`


## 📖 Manual
The objective is to simultaneously disband as many heavy balls as possible. To do this, simply place 
three balls with the same color in a horizontal row. Now these and all adjoining balls of this color 
will disappear. Five balls of the same color in a column will shrink to one heavy ball. 
Every 50 balls  you reach the next level and a new color will be added. 
Pay attention: One stack can only take up to eight balls.


## ⌨ Controls
 <kbd>←</kbd>/<kbd>→</kbd>: Move the crane  
 <kbd>↓</kbd>: Drop a ball  
 <kbd>N</kbd>: New Game  
 <kbd>B</kbd>: Change the ball set  
 <kbd>E</kbd>: Disable particle effects  
 <kbd>ESC</kbd>: Exit the game  
 <kbd>F2</kbd>: Toggle Fullscreen  
 <kbd>S</kbd>: Change game size (experimental)


## 🚀 Manual Setup
If you do not like the setup, you can use the portable zipped version.  
Then you have to install Java by your own, which is bundles with the setup.  
[<img alt="Download Zip" height="45px" src="https://raw.githubusercontent.com/tobsef/XSwing/media/download-zip.svg"/>](https://dl.xswing.net/XSwingPlus.zip)  
⚠ Cross Platform Zip - Java JRE not included! `XSwingPlus.zip` `32MB`
> 💡 Choose the **setup** for your platform, which is the **preferred installation option**.  
> Only advanced users should choose the _Download Zip_, wich needs ab installed Java JRE.

The game needs an installed Java runtime environment (JRE) with at least 8.  
You can get it for any platform [here](https://adoptium.net/temurin/releases/?version=8).  
For example on Windows choose:
 * Operating System: `Windows`
 * Architecture: `x64`
 * Package Type: `JRE`
 * Version: `8 - LTS`
And download the `.msi` installer.

If the `.jar` wile type is linked with the `java`, you can simply start the game by opening the `XSwingPlus.jar`.  
Otherwise, start the game with console:
```shell
java -jar XSwingPlus.jar
```

## ⚙ Configure IDE
To start developing, import the workspace als [Gradle](https://gradle.org) project in your IDE.
All dependencies are included in the `lib` folder. They should be automatically referenced by Gradle.  
To start the game simply run the Gradle task:
```shell
gradle run
```
This will compile the game and start the game with splash Screen (class `xswing.start.XSwingWithOptions`).
If you use [IntelliJ IDEA](https://www.jetbrains.com/idea/) you can also use the predefined run configurations:
 * ▶ `XSwing` 
 * ▶ `XSwingNoSplash` 


## 📦 Package
You can build the game for distribution wich wil generate a runnable jar which includes all dependencies,
resources and natives. 
```shell
gradle shadowJar
```
This will packe the game to the output folder: `build\libs\`.

> 💡 Keep in mind, that the game not only needs the resources in the `res` folder, but also the
> native [LWJGL](https://www.lwjgl.org) bindings for your platform 
> (`.ddl` for Windows, `.so` Linux and `.jnilib` for Mac).
> The `shadowJar` will copy all natives for all platforms to the build dir for you!


## 📜 Licence
This remake is a free fan project. _Software 2000_ is not involved in any way.
The company _Software 2000_ filed for bankruptcy in 2002. But the _Swing_ brand, graphics and sounds
may still be licenced by others. I tried to contact someone from the original team to get permission
to use the original theme - but without success. Another clone of the game for Android and IOS was 
also available under the name _Color-X-Plode_.  
The code is licenced under [MIT License](https://choosealicense.com/licenses/mit/).

I started the development in 2007 - A time when _git_ took its first steps and version control wasn't 
the first thing to do. This repository was restored from zipped code backups between 2007 and 2013.


### 🎵 Music
Thank you, [Michael Motschmann](https://soundcloud.com/mimo57m) (_MiMo57m_), for the great background music tracks.


## 🧱 Dependencies
 * [Slick2D](https://github.com/joshmarcus/slick2d) -
   2D Java game library based on LWJGL.
 * [LWJGL](https://www.lwjgl.org) -
   Lightweight Java Game Library.
 * [Nifty GUI](https://github.com/nifty-gui/nifty-gui) - 
   Open Source Java OpenGL GUI.
 * [Java 8](https://www.oracle.com/java/technologies/downloads/#java8) - 
   Programming Language and SDK.

[![Slick2D](https://raw.githubusercontent.com/tobsef/XSwing/media/banner_slick.png)](https://github.com/joshmarcus/slick2d)
[![LWJGL](https://raw.githubusercontent.com/tobsef/XSwing/media/banner_lwjgl.png)](https://www.lwjgl.org)  
[![Nifty GUI](https://raw.githubusercontent.com/tobsef/XSwing/media/banner_nifty.png)](https://github.com/nifty-gui/nifty-gui)
[![Java 8](https://raw.githubusercontent.com/tobsef/XSwing/media/banner_java.png)](https://www.oracle.com/java/technologies/downloads/#java8)


## 📋 Command Line Arguments
The game has several command line arguments wich can be overwritten.
This may be helpful for development or troubleshooting.

```properties
# Game
resolution = 1024 x 768
fullscreen = false
musicVolume = 20
fxVolume = 100

# Startup
showOptionPanelOnStart = true
checkForUpdates = false
uploadHighScore = false

# Debugging
debug = true
configFile = somePath/config.json

# Advanced
useNativeMouseCursor = false
```


## 👓 Modding
Is easy to change graphics ot sounds. Just replace them in the `res` folder.
For the balls, there is second theme `/res/balls2.png` which can be activated by the key <kbd>B</kbd>.

For theming there is a JSON configuration which stores all UI based settings: `config.json`.
Here you can change sounds and images and change settings like their position.
This configuration can even store multiple configurations at one.
Just change the `"configSetIndex": 1` (starting with `0`) to switch to a HD version with 16:9 aspect ratio 
with a higher resolution.