LIB  = lib/
SRC  = src/
DIST = dist/
BIN  = bin/
NS   = Sunseeker

LIBS  = $(LIB)RXTXComm.jar
FILES = $(SRC)main.java $(SRC)serial.java
JAR   = $(BIN)BatteryReceiver.jar

.PHONY: compile jar

all: compile jar

compile:
	javac -classpath $(LIBS) -d $(DIST) $(FILES)

jar:
	jar cvfe $(JAR) Sunseeker.Telemetry.Battery.Main -C $(DIST) $(NS)

run:
	@java -jar $(JAR)

clean:
	rm -rf $(DIST)$(NS)
	rm -f $(JAR)