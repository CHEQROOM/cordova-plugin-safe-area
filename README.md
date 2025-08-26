# cordova-plugin-safe-area

A cordova plugin to get the safe area insets on iOS and Android devices.

Implementation was inspired by the work in [capacitor-plugin-safe-area](https://github.com/AlwaysLoveme/capacitor-plugin-safe-area).

## Installation

```bash
cordova plugin add cordova-plugin-safe-area
```

## Usage

```javascript
cordova.plugins.safeArea
  .getInsets()
  .then((insets) => {
    console.log("Safe area insets:", insets);

    document.documentElement.style.setProperty(
      "--safe-area-inset-top",
      `${insets.top}px`
    );
    document.documentElement.style.setProperty(
      "--safe-area-inset-left",
      `${insets.left}px`
    );
    document.documentElement.style.setProperty(
      "--safe-area-inset-right",
      `${insets.right}px`
    );
    document.documentElement.style.setProperty(
      "--safe-area-inset-bottom",
      `${insets.bottom}px`
    );
  })
  .catch((error) => {
    console.error("Error getting safe area insets:", error);
  });
```
