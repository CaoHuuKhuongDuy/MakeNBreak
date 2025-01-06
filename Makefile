# Define paths and settings
JAVAFX_LIB := javafx-sdk-23.0.1/lib  # Modify with your JavaFX SDK path
SRC_DIR := src
BIN_DIR := bin
MAIN_CLASS := com.Main
JAVA_FILES := $(shell find $(SRC_DIR)/com -name "*.java")  # Use 'find' to gather all Java files

# The target to compile the Java files
all: compile

# Compile Java files using javac
compile:
	mkdir -p $(BIN_DIR)  # Ensure the bin directory exists
	javac --module-path $(JAVAFX_LIB) --add-modules javafx.controls -d $(BIN_DIR) $(JAVA_FILES)
	mkdir -p $(BIN_DIR)/resources/assets/images/
	mkdir -p $(BIN_DIR)/resources/assets/styles/
	cp -r $(SRC_DIR)/resources/assets/images/ $(BIN_DIR)/resources/assets/images/
	cp -r $(SRC_DIR)/resources/assets/styles/ $(BIN_DIR)/resources/assets/styles/



# Run the Java program
run: compile
	java --module-path $(JAVAFX_LIB) --add-modules javafx.controls -cp $(BIN_DIR) $(MAIN_CLASS)

# Clean up compiled class files
clean:
	rm -rf $(BIN_DIR)/*

# Phony targets to avoid conflicts with file names
.PHONY: all compile run clean
