# lego-picture #

This application takes a jpeg image and coverts it to an image that could be constructed by stacking lego bricks.

## Build ##
```
mvn package
```

## Execute ##
### Single image
```
java -jar target/lego-pictures-jar-with-dependencies.jar --input inputFile.jpg --output outputFile.png --colors colorPalettes/lego.csv --width 60
```
    
### Batch process
Use the process_files script to executing an image transform on all the images in a provided folder. Input arguments ar:
* Path to folder containing source images
* Path to color palette CSV file
* The width of the output images in brick units
* 

```
mvn package
./process_files.zsh ../sourceImageFolder colorPalettes/lego.csv 75
```