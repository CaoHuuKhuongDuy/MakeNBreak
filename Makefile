# Define paths and settings
JAVAFX_LIB := javafx-sdk-23.0.1/lib  # Modify with your JavaFX SDK path
SRC_DIR := src
BIN_DIR := bin
MAIN_CLASS := Main
JAVA_FILES := $(wildcard $(SRC_DIR)/*.java)

# The target to compile the Java files
all: compile

# Compile Java files using javac
compile:
	javac --module-path $(JAVAFX_LIB) --add-modules javafx.controls -d $(BIN_DIR) $(JAVA_FILES)

# Run the Java program
run: compile
	java --module-path $(JAVAFX_LIB) --add-modules javafx.controls -cp $(BIN_DIR) $(MAIN_CLASS)

# Clean up compiled class files
clean:
	rm -rf $(BIN_DIR)/*

# Phony targets to avoid conflicts with file names
.PHONY: all compile run clean
