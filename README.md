# Anti-Grian Switch reborn

This is a reproduction of the Anti-Grian switch from Hermitcraft season 9, and Anti-Gem switch from Hermitcraft season 10. It allows you to toggle an error that existed in the Continuity mod which did not play nice with sodium rendercode. But this can be used anywhere, that's right, anywhere! With (I hope) any mod.

## Use

By default, you configure the anti Grian switch using `CTRL + G`. You use the keybind `Should Control Be Held (AGS Reborn)(y/n)` to, as the name implies, configure wether or not the control key should be held. If it's set to `n`, than you don't need to hold control to activate it, if it's set to anything else, it will auto-set to `y`. If you place a gravity block when the switch is set to on, then the game will crash.

## How it works

This mod takes advantage of a strange quirk in how minecraft renders. This is based off a cross-compatibility error between [Continuity](https://modrinth.com/mod/continuity) and [Sodium](https://modrinth.com/mod/sodium) in 1.19 versions when [Indium](https://modrinth.com/mod/indium) where Sodium's rendering engine did not handle Fabric Rendering API calls correctly, and threw null pointer errors. My mod uses this code:

```Java
if(AntiGrianSwitchReborn.enableFallingEntityBug) {
    Object renderer = null;
    renderer.toString();
}
```

to crash the game when it tries to render a falling block entity. It is a renderthread crash, which is considered a "safe" crash. It should not corrupt your worlds¹ at least, it didn't in my testing.

---
_¹I DO NOT take **any** responsibility for corrupted worlds, server bans, or lost data caused by ~~abusing~~ leveraging this mod's core function_
