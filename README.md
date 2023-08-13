# <img src="https://raw.githubusercontent.com/tobsef/XSwing/main/.idea/icon.png" width="38"/> XSwing Plus

> Open Source remake of a classic match 3 arcade game.

[![gameplay](https://raw.githubusercontent.com/tobsef/XSwing/media/gameplay.gif)](https://youtu.be/BNxYl5GOIio)

## 💡 Trivia
The original game was named [Swing](https://en.wikipedia.org/wiki/Swing_(video_game)) (Marble Master)
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

## 💿 Download
### 👉 [XSwingPlus.zip](tobse.eu/games/xswing/release/XSwingPlus.zip) 32MB
Platform Independent for Windows, Linux and Mac

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

To use the app, you need at least a Java 1.8 JRE.
You can get it for any platform [here](https://adoptium.net/temurin/releases/).

## 🎮 Start
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

## 🚀 Setup
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

## 🧱 Dependencies
 * [Slick2D](https://github.com/joshmarcus/slick2d) -
   2D Java game library based on LWJGL.
 * [LWJGL](https://www.lwjgl.org) -
   Lightweight Java Game Library.
 * [Nifty GUI](https://github.com/nifty-gui/nifty-gui) - 
   Open Source Java OpenGL GUI.
 * [Java 1.8](https://www.oracle.com/java/technologies/downloads/#java8) - 
   Programming Language and SDK.