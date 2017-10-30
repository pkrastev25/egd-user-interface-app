# egd-user-interface

An Android Things application that serves as the User Interface for the "Electronic Guide Dog" project, HAW Hamburg.

## Core feature 

* Text-To-Speech with customazable language, voice pitch and speech rate

## Getting Started

The official Android Things guide for Raspberry Pi 3: [Guide](https://developer.android.com/things/hardware/raspberrypi.html)

## Project Structure

The project consists of the following directories (packagaes):
* activities - contains all the activities in the project
* app - contains the global application state
* constants - contains all constants and enums in the application
* controllers - contains modules which are responsible for controlloing/maintaining core features in the project
* tests - contains test cases for each module/feature
* utils - contains helper classes/methods that ease configuration

## Contribution/Versioning

The following steps should be fulfilled by every contributor for this project:
1. For every new feature, a separate branch must be created with a meaningful name about the feature (e.g. gpio-module, text-to-speech-feature, etc)
2. The commit messages should have one of the following prepositions:
* [ADD] - addition of a new file/logic
* [REMOVE] - deletion of an existing file/logic
* [CHANGE] - change to an existing file/logic
* Example commit message: [ADD] Logic for configuring GPIO ports
3. When the feature is tested and realized, a pull request to merge the branch to master must be opened. Assign as viewers all collaborators.
