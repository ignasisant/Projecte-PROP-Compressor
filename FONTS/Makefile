
JAVA := $(wildcard */*.java)
CLASS := $(wildcard */*.class)
NOM := entrega2.jar

ini:
	javac $(JAVA)

jar:
	
	jar -cvf ../EXE/$(NOM) $(CLASS)  
	@chmod +x ../EXE/$(NOM)
	@echo -e "\n\nL'executable $(NOM) es troba al directori EXE/"


clean:
	rm -f dades/*.class  domini/*.class  presentacio/*.class ../EXE/$(NOM)