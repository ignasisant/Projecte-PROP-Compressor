
JAVA := $(wildcard */*.java)
CLASS := $(wildcard */*.class)
FILES := $(shell echo '$(CLASS)' | sed 's/\$$/\\$$/g')
NOM := icetaton.jar

build:
	@javac   $(JAVA)
	@echo "[+]\tCompilat correctament.\nExecuta 'make jar' per generar l'executable."
jar:
	
	@jar cfm ../EXE/$(NOM) MANIFEST.MF $(FILES)
	@chmod +x ../EXE/$(NOM)
	@echo  "[+]\tOK!\nL'executable $(NOM) es troba al directori ../EXE/"
	@echo "Fes 'java -jar ../EXE/$(NOM)' per executar-lo. "

clean:
	@rm -f domini/*.class presentacio/*.class dades/*.class
