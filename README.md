[![Build Status](https://travis-ci.org/mjaakko/yeeja.svg?branch=master)](https://travis-ci.org/mjaakko/yeeja)
[![codecov](https://codecov.io/gh/mjaakko/yeeja/branch/master/graph/badge.svg)](https://codecov.io/gh/mjaakko/yeeja)
![GitHub](https://img.shields.io/github/license/mjaakko/yeeja.svg)
[![](https://jitpack.io/v/xyz.malkki/yeeja.svg)](https://jitpack.io/#xyz.malkki/yeeja)
# yeeja

**yeeja** is a Java implementation of Yeelight remote control protocol. 
**yeeja** implementation is based on [Yeelight Inter-Operation 
Spec](https://www.yeelight.com/download/Yeelight_Inter-Operation_Spec.pdf).

## Supported features

* Device discovery in local network
* Control commands
* State change notifications

## Download

1. Add [JitPack.io](https://jitpack.io/) repository
```
repositories {
	...
	maven { url 'https://jitpack.io' }
}
```
2. Add **yeeja** as dependency
```
dependencies {
	...
	implementation 'xyz.malkki:yeeja:1.1'
}
```

## Documentation

* [Javadoc](https://javadoc.jitpack.io/com/github/mjaakko/yeeja/1.1/javadoc/index.html)

