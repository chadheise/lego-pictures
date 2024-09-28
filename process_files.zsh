#!/bin/zsh

# Check if a directory path was provided
if [ -z "$1" ]; then
    echo "Usage: $0 /path/to/directory /path/to/colors.csv <width>"
    exit 1
fi

# Check if a colors was provided
if [ -z "$2" ]; then
    echo "Usage: $0 /path/to/directory /path/to/colors.csv <width>"
    exit 1
fi

# Check if a width was provided
if [ -z "$3" ]; then
    echo "Usage: $0 /path/to/directory /path/to/colors.csv <width>"
    exit 1
fi

# Assign the args to variables
DIR="$1"
COLORS="$2"
WIDTH="$3"

# Check if the provided path is a valid directory
if [ ! -d "$DIR" ]; then
    echo "Error: $DIR is not a valid directory."
    exit 1
fi

OUTPUT_DIR="$DIR/output"

# Check if output directory exists and create it if not
if [ ! -d "$OUTPUT_DIR" ]; then
    mkdir -p "$OUTPUT_DIR"
    echo "Created output directory: $OUTPUT_DIR"
fi


#
COLORS_FILE_NAME=$(basename "$COLORS")
COLORS_FILE_NAME_WITHOUT_EXTENSION="${COLORS_FILE_NAME%.*}"
echo $COLORS_FILE_NAME_WITHOUT_EXTENSION

# Get the current timestamp
TIMESTAMP=$(date +"%Y-%m-%d_%H-%M-%S")
echo "Current timestamp: $TIMESTAMP"
echo "Width: $WIDTH"


# Loop through each file in the directory
for INPUT_FILE_PATH in "$DIR"/*; do
    if [ -f "$INPUT_FILE_PATH" ]; then
        INPUT_FILE_NAME=$(basename "$INPUT_FILE_PATH")
        INPUT_FILENAME_WITHOUT_EXTENSION="${INPUT_FILE_NAME%.*}"
        
        OUTPUT_FILE_PATH="$OUTPUT_DIR/$INPUT_FILENAME_WITHOUT_EXTENSION-$COLORS_FILE_NAME_WITHOUT_EXTENSION-$WIDTH-$TIMESTAMP.png"
        
        echo 'java -jar target/lego-pictures-jar-with-dependencies.jar --input "$FILE" --output "$DIR/output_$FILE" --colors colorPalettes/bambuLabPla.csv --width "$WIDTH"'
        java -jar target/lego-pictures-jar-with-dependencies.jar --input "$INPUT_FILE_PATH" --output "$OUTPUT_FILE_PATH" --colors colorPalettes/bambuLabPla.csv --width "$WIDTH"
    fi
done