# Java-HWX
Ori Adkins and Ryan Rowe Java HW X (Battleship)
## Usage
### How to run
Battleship.java is the 'main' class in this program, it is packed into a Jar archive for easy access. To run:
``` java -jar Battleship.jar ```

## Description

This is a simple Battleship game, implemented in Java. It features a 2 player mode, that you can play with a friend!

# Start up screen

Implemented with a JLayeredPane that allows for the buttons to be placed near the bottom corner of the screen, the user has the option to click play, or view the rules.

# Gameplay

The user is displayed a simple screen (JDialog and Buttons) to place ships, once they are done placing ships they begin the battle. The first player to sink all ships wins.

# Issues

There are a couple bugs in this program, first and foremost it is missing the ability to rotate ships horizontally. This is unfortunate, but ship rotation ended up being a challenge for
some reason. The rotation exists in the code, but ships are always placed vertically, even though they may be checked horizonatally depending on the value of isHorizontal.
