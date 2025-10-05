# Anti-Grian Switch reborn

This mod is a recreation of the Anti-Grian Switch from Hermitcraft Season 9 (and Anti-Gem Switch from Season 10). It reproduces a deliberate rendering crash caused by a quirk in how Minecraft handles falling block entities. While originally related to cross-compatibility issues between Continuity and Sodium , this mod can be used in any environment to trigger the effect.
## Use

Toggle the switch: Press CTRL + G (default).

Control requirement configuration: Use the keybind labeled Should Control Be Held (AGS Reborn) (Y/N):

N: You don’t need to hold Control to toggle the switch.

Any other value: Control must be held to toggle.

## How it works

This mod exploits a quirk in Minecraft's rendering system. When enabled, the mod iterates over all loaded entities, and if an entity is a falling block, it deliberately triggers a NullPointerException on the render thread:
```Java
    if (obj instanceof EntityFallingBlock) {
        Object renderer = null;
        renderer.toString(); // deliberate crash
    }
```

to crash the game when it tries to render a falling block entity. It should not corrupt your worlds¹ at least, it didn't in my testing.

---
_¹I DO NOT take **any** responsibility for corrupted worlds, server bans, or lost data caused by ~~abusing~~ leveraging this mod's core function_
