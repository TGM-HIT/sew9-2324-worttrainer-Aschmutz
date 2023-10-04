# Wordtrainer 
The Wordtrainer is a utility made to learn the spelling of Words.
You can either create a Wordtrainer yourself, or use the prebuild JAR version and insert your own Words using the `defaultWordtrainer.json` file. To get further information please read the [Persistence](#persistence) chapter. 
## Structure
The Wordtrainer consists of three parts:
- [The Wordpairs](#the-wordpairs)
- [The WordtrainerBackend](#the-wordtrainerbackend)
- [The WordtrainerGui](#the-wordtrainergui)

Which can together be used to form a Wordtrainer Application.

### The Wordpairs
These are the Base-concept of the Wordtrainer. A Wordpair consists of a Valid Image URL and a Word that should be descriptive of the image.
The URL given in a Wordpair mus be a URL leading directly to an IMAGE, and the word will, as of now, be checked case-sensitive, so look out for that.    
Furthermore, the url passed into the Wordpair must be usable in a Java URL Object, meaning it must be a full domain starting at https://...
### The WordtrainerBackend
This is the Part of the Wordtrainer containing all the Wordpairs, managing the Statistics, and also the only part that needs to be saved in order to achive [persistence](#persitence).
The WordtrainerBackend also has save and load methods as well as constructors, taking a path for creating directly from a loaded file. In order to use these functionalities, there must be a WordtrainerSaveManager set preemptively, ether via the constructor or the respective setter method. It is important to note that the WordtrainerBackend itself does NOT have a save and loading functionality build in, and relies on the save and load implementation of the given WordtrainerSaveManager Implementation.
### The WordtrainerGui
This is the part of the Wordtrainer that is responsible for actually displaying the Game but also, despite the name, is responsable for controlling the words that are used, and handels the Wordtrainerbackend directly.
So if you where to sort the Individual methods into an MVC pattern, the WordtrainerBackend would be the Model and the WordtrainerGui would be the Controller. As the WordtrainerGui delegates everything UI related to the JOptionPane Class.
The WordtrainerGui uses the validate methods of the Wordpairs to validate the input. So in order to change this functionality, please modify the  functionality of the Wordpairs.
### StatTpype
This is an Enum used to identify which Value is requested / to be set when handling the Statistics of the WordtrainerBackend.
## Persistence
The persistence is realized via the WordtrainerSaveManager interface. This interface offers a load() and a save() method.
Please note that a Persistence Implementation will be needed in order to use save and load functionality integrated within the WordtrainerBackend.
### The WordtrainerJSONSaveManager
This is an Implementation of WordtrainerSaveManager.  It uses the JSON file format to store the Information of a WordtrainerBackend, where to output could look something like this: 
```json
{"wordpairs":[{"word":"tgm","imageUrl":"https://upload.wikimedia.org/wikipedia/commons/b/ba/TGM_Logo.png"},{"word":"untis","imageUrl":"https://neilo.webuntis.com/assets/images/logo.png"}],"correct":10,"wrong":90}
```
so if we were to expand this we would get:
```json
{
  "wordpairs":[
    {
      "word":"tgm",
      "imageUrl":"https://upload.wikimedia.org/wikipedia/commons/b/ba/TGM_Logo.png"
    },
    {
      "word":"untis",
      "imageUrl":"https://neilo.webuntis.com/assets/images/logo.png"
    }
  ],
  "correct":10,
  "wrong":90
}
```
This means in order to Add more Wordpairs to the Wordtrainer without modifying the actual code, you can just add another 
```json
{
  "word":"newWord",
  "imageUrl":"newUrl"
}
```
seperated by a "," from the previous