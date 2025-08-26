import UIKit

@objc(SafeArea) class SafeArea: CDVPlugin {
    @objc(getInsets:)
    func getInsets(command: CDVInvokedUrlCommand) {
        var top = 0, bottom = 0, left = 0, right = 0
        if #available(iOS 11.0, *) {
            let insets = UIApplication.shared.keyWindow?.safeAreaInsets ?? UIEdgeInsets.zero
            top = Int(insets.top)
            bottom = Int(insets.bottom)
            left = Int(insets.left)
            right = Int(insets.right)
        }

        let result: [String: Int] = [
            "top": top, "bottom": bottom, "left": left, "right": right
        ]
        let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: result)
        self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
    }
}
