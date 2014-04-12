BUILD=bin
JAVAC=javac
JAVA=java -cp $(BUILD)
CURL=

compile:
	rm -rf $(BUILD)
	mkdir $(BUILD)
	$(JAVAC) src/*.java -d $(BUILD)

run: compile
	$(JAVA) Main $(CURL)

submit: compile
	try jeh-grd plc-curl Makefile src/*.java
