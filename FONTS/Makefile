
JAVA := $(wildcard */*.java)
CLASS := $(wildcard */*.class)
NOM := entrega2.jar



jar:
	javac $(JAVA)
	jar -cvf ../EXE/$(NOM) $(CLASS)  
	@chmod +x ../EXE/$(NOM)
	@echo -e "\n\nL'executable $(NOM) es troba al directori EXE/"


clean:
	rm -f dades/*.class  domini/*.class  presentacio/*.class ../EXE/$(NOM)