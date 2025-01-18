# Medieval Factions

## Description 
Medieval Factions is a system of mechanics that allows for the simulation of sovereign nations in Minecraft. Players can create nations, claim territory, engage in warfare or politics, write laws or hold dueling tournaments, and generally are able to attempt to recreate society somewhat.

A list of features can be found [here](https://github.com/dmccoystephenson/Medieval-Factions/wiki/Features).

The development of the fifth major version of MF was led by [alyphen](https://github.com/alyphen) (creator of [RPKit](https://github.com/RP-Kit/RPKit/wiki)). Huge thanks to her!

## Installation
### First Time Installation
1) You can download the plugin from [this page](https://www.spigotmc.org/resources/medieval-factions-sovereign-nation-simulator.79941/updates).
2) Once downloaded, place the jar in the plugins folder of your server files.
3) Restart your server.

### Dynmap Integration
Dynmap has been integrated with this plugin. In order to be able to view claimed land on a dynamic map, download and install the plugin [here](https://www.spigotmc.org/resources/dynmap.274/).

### Expansions
1) [Fiefs](https://github.com/dmccoystephenson/Fiefs)
2) [Currencies](https://github.com/dmccoystephenson/Currencies)

## Usage
- [User Guide](https://github.com/dmccoystephenson/Medieval-Factions/wiki/Guide)
- [List of Commands](https://github.com/dmccoystephenson/Medieval-Factions/wiki/Commands)
- [FAQ](https://github.com/dmccoystephenson/Medieval-Factions/wiki/FAQ)
- [Config Options](https://github.com/dmccoystephenson/Medieval-Factions/wiki/Config-Options)
- [Faction Flags](https://github.com/dmccoystephenson/Medieval-Factions/wiki/Faction-Flags)
- [List of Placeholders](https://github.com/dmccoystephenson/Medieval-Factions/wiki/Placeholders)

## Support
You can find the support discord server [here](https://discord.gg/xXtuAQ2).

### Experiencing a bug?
Please fill out a bug report [here](https://github.com/dmccoystephenson/Medieval-Factions/issues/new/choose).

- [Known Bugs](https://github.com/dmccoystephenson/Medieval-Factions/issues?q=is%3Aopen+is%3Aissue+label%3Abug)

## Contributing
- [Contributing.md](https://github.com/dmccoystephenson/Medieval-Factions/blob/master/CONTRIBUTING.md)
- [Notes for Developers](https://github.com/dmccoystephenson/Medieval-Factions/wiki/Developer-Notes)

## Testing
### Unit Tests
To run the unit tests, you can use the following command:

Linux:
```bash
./gradlew clean test
```
Windows:
```cmd
.\gradlew.bat clean test
```

If you see BUILD SUCCESSFUL, then the tests have passed.

### Looking to create an add-on plugin?
I recommend using [FactionsBridge](https://www.spigotmc.org/resources/factionsbridge.89716/) by [Retrix_Solutions](https://www.spigotmc.org/resources/authors/retrix_solutions.491191/). It would make your add-on plugin usable across a number of factions implementations.

Alternatively, you can use the External API, the documentation for which can be found [here](https://github.com/dmccoystephenson/Medieval-Factions/wiki/External-API-Documentation).

## Authors and acknowledgement
### Developers
| Name                                                                          | Main Contributions                                                                                                                          |
|-------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|
| Daniel Stephenson                                                             | Original creator                                                                                                                            |
| [Ren Binden](https://github.com/alyphen)                                      | Created Medieval Faction 5                                                                                                                  |
| [Pasarus](https://github.com/Pasarus)                                         | Overhauled the Storage Manager to use UUIDs and JSON and made other improvements to the plugin                                              |
| Caibinus                                                                      | Implemented Duels, Gates and Dynmap Integration                                                                                             |
| [Callum](https://www.spigotmc.org/resources/authors/retrix_solutions.491191/) | Created event classes, overhauled the Command System, implemented PlaceholderAPI integration and made many other improvements to the plugin |
| Richardhyy                                                                    | Fixed some encoding issues                                                                                                                  |
| Mitras2                                                                       | Implemented ActionBar territory alerts                                                                                                      |
| [Kaonami](https://github.com/Daniels7k)                                       | Fixed a typo in the README                                                                                                                  |
| GoodLucky777                                                                  | Fixed a bug and a few typos in the code                                                                                                     |
| Elafir                                                                        | Made it possible to control gates with redstone                                                                                             |
| [Deej](https://github.com/Mr-Deej)                                            | Added checks to several commands                                                                                                            |
| VoChiDanh                                                                     | Refactored parts the PersistentData class in an attempt to resolve java compatibility issues                                                |
| Kyrenic                                                                       | Implemented contiguous claims config option                                                                                                 |
| [Tems](https://github.com/Tems-py)                                            | Fixed claim protection issues                                                                                                               |
| MestreWilll                                                                   | Contributed Brazilian Portueguese translation                                                                                               |  

### Translators
| Name                                                             | Language(s)          |
|------------------------------------------------------------------|----------------------|
| Khanter                                                          | Spanish              |
| Neh                                                              | Spanish              |
| Johnny                                                           | Spanish              |
| lilhamoood                                                       | Spanish              |
| 1barab1                                                          | Russian              |
| 2kManfridi                                                       | Russian              |
| [Kaonami](https://github.com/Daniels7k)                          | Portuguese Brazilian |
| [graffity_X](https://www.spigotmc.org/members/kicker765.946561/) | German               |
| JustGllenn                                                       | Dutch                |
| TDL                                                              | Dutch                |
| [n0virus](https://www.youtube.com/c/n0virus)                     | Dutch                |
| MestreWilll                                                      | Brazilian Portuguese |   

I created this plugin because I wanted to use the original [Factions](https://www.spigotmc.org/resources/factions.1900/) plugin for an upcoming server of mine, but it wasn't updated for the version of minecraft I was going to be using. I decided to take inspiration from the concept of factions - groups of players that can claim land - and create my own factions plugin.

The first release version, [v1.7](https://github.com/dmccoystephenson/Medieval-Factions/releases/tag/v1.7), was released on SpigotMC in June 2020 and looked much different than the plugin does today.

I am extremely grateful to those that have donated their time improving the project, one way or another. The plugin wouldn't be where it is today without the contributions of others.

## License
GPL3

## Project Status
This project is in active development.

### bStats
You can view the bStats page for the plugin [here](https://bstats.org/plugin/bukkit/Medieval%20Factions/8929).
