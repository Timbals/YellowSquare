# YellowSquare

This is a game created in 14 days for the [Game++ Community Challenge 7](https://www.youtube.com/watch?v=ifkPrdfoKZI) created by Youtube User [LetsGameDev](https://www.youtube.com/user/Tomzalat).

The goal of the challenge was to create a game within 14 days with a in with the dimensions 128x128 pixel. 

The game achieved the [10th place out of 53](https://www.youtube.com/watch?v=w-p8R-heAnY).

Created using [Slick2D](http://slick.ninjacave.com/) which utilises [LWJGL](https://www.lwjgl.org/).

## Screenshots

![Screenshot 1](http://lets-gamedev.de/gppcc7/screenshot/38_1.png)
![Screenshot 2](http://lets-gamedev.de/gppcc7/screenshot/38_0.png)

## Features

- a playable yellow square controlled with WASD
- the ability to shoot bullets with the mouse
- 4 randomly generated stages per run
	- each stage has a starting room
	- each stage has a number of rooms that increases in later stages
	- a boss room that is as far away from the starting room as it can get
- a maximum of 5 hearts/lifes starting with 3
- a 40% chance per finished room to spawn a chest
- 3 types of upgrades found in chests
	- ATKSPEED + increases the attack speed
	- BULLETSIZE + increases the bullet size
	- LIFE + adds a life (max. 5)
- 6 types of enemies
	- Searcher: tries to hurt you by touching you
	- Tower: stationary; shoots bullets at you
	- ClueTower: stationary; shoots homing bullets at you
	- Worm: moves in random directions; has multiple parts that can be destroyed individually; if you hit the head it will instantly be destroyed
	- TowerBoss: big tower; shoots waves of bullets at you; spawns towers
	- SearcherBoss: big searcher; tries to hurt you by touching you; after reaching half life it will spawn searchers after every hit

## License

[Licensed under the MIT License](https://github.com/Timbals/YellowSquare/blob/master/LICENSE.md).