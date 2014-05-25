## README ##

###Sänkoss Project for TDA367 @ Chalmers University of Technology

![Main Menu](https://raw.githubusercontent.com/Bipshark/TDA367/develop/screenshots/mainmenu.png)

### Documentation
Requirements and Analysis Document (RAD) and Software design description (SDD), as well as the UI Mockup can be found in the root of the repository in the folder [documentation](documentation).

### Settings
Settings can be loaded through an external INI-file ([settings.ini](desktop/settings.ini)) located in the root of the application. This file will be auto generated first time the application is run.

#####Contents of settings.ini
* hostname - domain where server application is run
* port - port to route communication through (default: 30401)

### Downloads
Game can be downloaded [here](http://tinyurl.com/qyccdt5) for free.

### Testing

The application (both server and client side) has been tested using JUnit. 


Getters, setters and similar methods (e.g is-methods) has not been tested.

### License
The project is licensed under [The MIT license](http://opensource.org/licenses/MIT), which means that you may, without limitation, use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the software, free of charge.

All downloaded sprites is licensed under the [CC-BY 3.0](https://creativecommons.org/licenses/by/3.0/) license. Sources can be found in APPENDIX of the [RAD](documentation/RAD).