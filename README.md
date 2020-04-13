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

## Examples

### Discover Yeelights in local network

```java
YeelightDiscovery discovery = new YeelightDiscovery(new DefaultDatagramSocketFactory(), new DefaultInetAddressFactory());

List<YeelightDevice> yeelights = disovery.discover();
for (YeelightDevice yeelight : yeelights) {
  String id = yeelight.getId();
  String address = yeelight.getAddress();
  int port = yeelight.getPort();
  YeelightModel model = yeelight.getModel();
}
```

### Run command

```java
Yeelight yeelight = /* YeelightDevice from previous example */

try (YeelightConnection connection = YeelightConnection.connect(new DefaultSocketFactory(), new DefaultInetAddressFactory(), yeelight, null /*No notification listener*/)) {
  //Toggle power
  connection.runCommand(new Toggle());

  //Get power state and brightness
  YeelightProps props = connection.runCommand(new GetProp("power", "bright"));
  boolean power = props.getProp("power");
  int brightness = props.getProp("bright");
} catch (YeelightCommandException e) {
  //Failed to execute Yeelight command
  int code = e.getCode(); //Error code returned by Yeelight
  String message = e.getMessage(); //Error message returned by Yeelight
} catch (IOException e) {
  e.printStackTrace();
}
```

### Listen state

```java
YeelightDevice yeelight = /* YeelightDevice */

YeelightNotificationListener notificationListener = new YeelightNotificationListener() {
    @Override
    public void onNotification(@NotNull YeelightProps props) {
        if (props.hasProp("power")) {
            boolean powerOn = props.getProp("power");
            System.out.println("Yeelight turned "+(powerOn ? "on" : "off"));
        }
    }

    @Override
    public void onError(@NotNull IOException e) {
        //Connection error
        e.printStackTrace();
    }
};

try (YeelightConnection connection = YeelightConnection.connect(new DefaultSocketFactory(), new DefaultInetAddressFactory(), yeelight, notificationListener)) {
    connection.runCommand(new Toggle());
} catch (IOException | YeelightCommandException e) {
    e.printStackTrace();
}
```

### Use a specific network (Android)

On Android devices, you might want to specify which network is used for Yeelight connections. This can be done by creating factories that use [Android's Network API](https://developer.android.com/reference/android/net/Network).

For example, a SocketFactory that binds sockets to a specific network:
```java
public class NetworkSocketFactory implements SocketFactory {
    private Network network;

    public NetworkSocketFactory(Network network) {
        this.network = network;
    }

    @Override
    public Socket create() {
        Socket socket = new Socket();
        network.bindSocket(socket);
        return socket;
    }
}
```

## Documentation

* [Javadoc](https://javadoc.jitpack.io/com/github/mjaakko/yeeja/1.1/javadoc/index.html)

