LIB  = lib/
SRC  = src/
DIST = dist/
BIN  = bin/
NS   = Sunseeker

LIBS  = $(LIB)RXTXComm.jar
FILES = $(SRC)Main.java $(SRC)Serial.java $(SRC)Pack.java $(SRC)Interface/MainInterface.java $(SRC)Interface/PortInterface.java $(SRC)Interface/PackInterface.java $(SRC)Handler/SerialHandler.java $(SRC)Handler/SessionHandler.java $(SRC)Handler/PageHandler.java $(SRC)Event/Dispatcher.java $(SRC)Event/Event.java $(SRC)Event/Listener.java
JAR   = $(BIN)BatteryReceiver.jar

.PHONY: compile jar

all: compile jar

compile:
	javac -classpath $(LIBS) -d $(DIST) $(FILES)

jar:
	jar cfm $(JAR) manifest.txt -C $(DIST) $(NS)

run:
	@java -jar $(JAR)

clean:
	rm -rf $(DIST)$(NS)
	rm -f $(JAR)
