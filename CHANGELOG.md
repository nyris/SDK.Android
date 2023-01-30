# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## 1.7.4 - 30.01.2023
* Fix a crash related to wrong conversion Int to Float related to Region Model

## 1.7.3 - 27.01.2023
### Fixed 
* a bug related to xoptions which was blocking the image search api to work properly

## 1.7.2 - 27.01.2023
### Updated
* Disable the missing OcrOptions
* Code cleanup 

## 1.7.1 - 27.01.2023
### Updated
* Deprecated the xoptions parameters, expected to be removed on the release 1.8.0
* Updated the README file

## 1.7.0 - 10.01.2023
### Added 
* Filters option

### Updated
* Migrate find/1.0 to find/1.1
* Update readme file 

### Removed
* gradle flavors

## 1.6.1 - 22.11.2022
### Added
* Added workaround to allow users to consume the new sdk version without conflicting with the android flavors setup.  

## 1.6.0 - 09.11.2022
### Added 
* Feedback api implementation 
* Config that allows to config the sdk based on URLS, RETRY, ...
* Migration of the matching api v2

### Updated 
* Library DI setup 
* Gradle dependencies
* AGP
* README

### Removed 
* Old deprecated code
* Removed old implementation of the object proposals 
* URL builders

## 1.5 - 13.09.2018
### Added
- xOptions feature to image and text search
- deprecation of some methods in classes image search && text search api

### Modified
- image search to support the new advanced offer querying
- text search to support the new advanced offer querying

## 1.4 - 31.08.2018
### Added
- Add category prediction

## 1.3 - 27.08.2018
### Added
- Add offer recommendation feature

## 1.2 - 24.05.2018
### Added
- Matching offer using float array

## 1.1.1 - 24.05.2018
### Fixed
- Models obfuscation 
 
## 1.1 - 14.05.2018
### Added
- Kotlin extension to help dispose `Disposable` object easily

### Updated
- Updated demo app

## 1.0 - 21.03.2018
### Added
- README now contains how to use nyris SDK
- A License file
- SDK library for nyris Services
- A demo app that show how you can use a simple image matching