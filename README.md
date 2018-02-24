# SadConfigs
Very sad configs :kappa: for my plugins made in Kotlin cuz why not lol

### How to use?
SadConfigs is simple API for configuration files for Bukkit plugins
To get value from config there is method: ConfigurationManager.getFieldValue(configuration: Configuration, name: String)
If field with given name exists it will return its value, if not it will return null, if file is empty it will throw ConfigurationException

To set value in config there is method: ConfigurationManager.setField(configuration: Configuration, name: String, value: Any)
If field with given name doesn't exists it will throw ConfigurationException

If you're making config and you want to comment something theres method for it as well String.comment(comment: String)
it works only in config classes and you use it as `"exampleField".comment("Example Comment") to 1`
in file it will look like:
```YML
#Example comment
exampleField: 1
```

### Examples

## Example config class

```Kotlin
class ExampleConfig: Configuration {
 
  override val folder: File = ExamplePluginMainClass.getPlugin().getDataFolder() //Folder in which file will be saved
  
  override val fileName: String = "ExampleConfig" //Default set to config, use it only if you want file with other name
  
  //Values saved in config
  override val values: HashMap<String, Any> = hashMapOf(
    "exampleField".comment("Example Comment") to 1,
    "sadConfig" to true,
    "verySad" to "so fucking sad")
    
    
}
```

To register it go to your plugin's main class and in `onEnable()` add `ConfigurationManager.registerNewConfiguration(ExampleConfig())`

## Example config file
```YML
#Example Comment
exampleField: 1

sadConfig: true

verySad: 'so fucking sad'
```
