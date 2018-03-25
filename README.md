# egd-user-interface

An Android Things IoT application that serves as the User Interface for the "Electronic Guide Dog" project, part of the Department of Electrical Engineering and Computer Science, HAW Hamburg.

## EGD Project

The Electronic Guide Dog project aims to find an alternative to the guide dog of the visually impaired people. As an electronic device, it can provide multiple functionalities to the user, as well as overcome the limited usage of the real guide dog. This project seeks to improve the life quality of the visually impaired people.
After careful discusion among all participants in the project, five teams have been created who take care of a certain aspect of the overall application. One of the aspects is the user interface. The user interface is responsible for realizing the interaction between the user and the device. It is a necessery component in the overall application, without the user interface, the user cannot use the device. 

### User interface aspects

This section outlines the specific aspects of the user interface that had focus during this semester. After multiple discussions and research, the members of the user interface team had decided to focus only on certain points. The choice for a certain aspect is based on the requirement for the electronic guide dog, as well as personal interest and preference. In addition, a meeting took place with visually impaired people. Participants in the project were given the possibility to discuss possible features and requirements for the application. Evidence for realizing these aspects were also found in the feedback given by the visually impaired people.

#### Concept of the user interface

The concept of the user interface seeks to find an optimum solution for realizing the interaction between the user and the device. In addition, the user interface concept should also take the responsibility of communication with all other modules in the Electronic Guide Dog application. 

#### Hardware aspect of the user interface

This aspect deals with the physical dimensions of the user interface. It includes the realization of all circuits, buttons, external components that the user will use in order to interact with the device.

#### Voice interface

The voice interfaces aims to incorporate a very big part of the interaction process. Voice commands are a very efficient way of reaching functionality that the user needs. In this particular use case, the voice interface is a very good addition to the user interface, since traditional ways of interaction are not possible (e.g. a screen with text and buttons).

#### Lost/Forgotten Electronic Guide Dog feature

This aspect deals with a use case in which the user has lost the Electronic Guide Dog. Since the aim of the project is to replace the guide dog and the white cane, the visually impaired people are so accustomed to; losing the electronic device would be a crucial case that should be avoided. The interviewed visually impaired cane users told it would have been "nice to have it", because cases of forgetting or losing a stick might not be often, but losing the electronic one could "hit them both emotionally and financially". The research through other "electronic cane" projects available in internet shows there are almost 100% no project offering such functionality. The feature envisages the use of the connection of Bluetooth-modules in both Raspberry Pi 3 and smartphone for two purposes: checking the distance range and giving navigational commands by voice after going over the distance threshold.

## Core features

* Integration with a Raspberry Pi 3 microcontroller
* Text-To-Speech with customazable language, voice pitch and speech rate
* Speech recognition with a predefined context
* Appropriate error handling

## License

   Copyright 2018 Petar Krastev

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
