
JAVA := $(wildcard */*.java)
CLASS := $(wildcard */*.class)
FILES := $(shell echo '$(CLASS)' | sed 's/\$$/\\$$/g')
NOM := icetaton.jar

build:
	@javac   $(JAVA)
	@echo "Compilat correctament.\nExecuta 'make jar' per generar l'executable."
jar:
	
	@jar cfm ../EXE/$(NOM) MANIFEST.MF $(FILES)
	@chmod +x ../EXE/$(NOM)
	@echo  "OK!\n\nL'executable $(NOM) es troba al directori ../EXE/"


clean:
	@rm -f domini/*.class presentacio/*.class dades/*.class
