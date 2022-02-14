![icon-small](https://user-images.githubusercontent.com/84018133/121270201-87f29f00-c8c1-11eb-8bb1-1bd4d8076a93.png)

This is a fully configurable gamma/brightness utility mod, making it much easier to see in the dark. 
It's a really basic mod that allows you to change the gamma value in game way higher than the in game brightness setting allows. 
Nights will look like day and caves will look like they're fully lit up. 
Basically Gammabright but for fabric.

## **Functions**

**Toggle Gamma**  
*Usage: Keybind (default: G) or /gamma toggle*  
Toggle between the default gamma percentage and the configured toggled percentage.

**Increase Gamma**  
*Usage: Keybind (default: arrow up) or /gamma increase <value> (value is optional)*  
Increase the gamma percentage by the configured step percentage or the given value.

**Decrease Gamma**  
*Usage: Keybind (default: arrow down) or /gamma decrease <value> (value is optional)*  
Decrease the gamma percentage by the configured step percentage or the given value.

**Set Gamma**  
*Usage: /gamma set <value>*  
Set the gamma to a specific percentage.
 
**Min Gamma**  
*Usage: /gamma min*  
Sets the gamma to the configured minimum limit. 
 
**Max Gamma**  
*Usage: /gamma max*  
Sets the gamma to the configured maximum limit. 

## **Configuration (File / Mod Menu)**

**Default Gamma Percentage**  
*Default: 100%*  
Gamma percentage for when the mod is toggled off.

**Toggled Gamma Percentage**  
*Default: 1500%*  
Gamma percentage for when the mod is toggled on.

**Smooth Transition**  
*Default: false*  
Make the transition when toggling gamma smoother.
 
**Transition Speed**  
*Default: 4500*  
Speed in gamma percentage per second

**Update Toggle Value**  
*Default: false*  
Enabling this will make the toggle value remember the last non default value you've used.

**Gamma Percentage Step**  
*Default: 10%*  
Gamma percentage change for every increase or decrease.

**Limit Check**  
*Default: true*  
Should the mod enforce the minimum and maximum gamma limits.

**Minimum Gamma Limit**  
*Default: -750%*  
Minimum gamma percentage the mod will allow if the Limit Check is enabled.

**Maximum Gamma Limit**  
*Default: 1500%*  
Maximum gamma percentage the mod will allow if the Limit Check is enabled.

**Show Gamma Message**  
*Default: true*  
Show a message with the new gamma value.

**Reset on Close**  
*Default: false*  
Reset the gamma value to default when Minecraft closes.

## **Dependencies**

**Included:**  
[Cloth Config API Fabric](https://github.com/shedaniel/cloth-config) (Is required to make the config work)
 		
**Required:**  
[Fabric API](https://github.com/FabricMC/fabric)

**Optional:**  
[Mod Menu](https://github.com/TerraformersMC/ModMenu) (This mod allows you to edit the configs in game)
