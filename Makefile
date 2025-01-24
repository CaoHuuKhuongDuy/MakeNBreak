# Define paths and settings
JAVAFX_LIB := javafx-sdk-23.0.1/lib  # Modify with your JavaFX SDK path
SRC_DIR := src
BIN_DIR := bin
DIST_DIR := dist
MAIN_CLASS := com.Main
JAVA_FILES := $(shell find $(SRC_DIR)/com -name "*.java")  # Use 'find' to gather all Java files

# The target to compile the Java files
all: jar

# Compile Java files using javac
compile:
	mkdir -p $(BIN_DIR)  # Ensure the bin directory exists
	javac --module-path $(JAVAFX_LIB) --add-modules javafx.controls -d $(BIN_DIR) $(JAVA_FILES)

# Package the project into a runnable JAR file
jar: compile
	mkdir -p $(DIST_DIR)
	echo "Main-Class: $(MAIN_CLASS)" > MANIFEST.MF
	jar cfm $(DIST_DIR)/MyApp.jar MANIFEST.MF -C $(BIN_DIR) . -C $(SRC_DIR) .
	rm -f MANIFEST.MF

# Run the Java program
run: jar
	java --module-path $(JAVAFX_LIB) --add-modules javafx.controls -jar $(DIST_DIR)/MyApp.jar

# Clean up compiled class files and JARs
clean:
	rm -rf $(BIN_DIR)/* $(DIST_DIR)/*

# Phony targets to avoid conflicts with file names
.PHONY: all compile jar run clean
