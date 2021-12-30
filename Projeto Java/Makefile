JFLAGS =  -d .
JC = javac

.SUFFIXES: .java .class 

.java.class:
	$(JC)  $(JFLAGS)  $*.java

CLASSES =  \
		Controller/Exceptions/InvalidException.java \
		Model/Interfaces/IReview_Struct.java \
		Model/Catalogos/Review_Struct.java \
		Model/Interfaces/IBusiness_Struct.java \
		Model/Catalogos/Business_Struct.java \
		Model/Interfaces/IUser_Struct.java \
		Model/Catalogos/User_Struct.java \
		Model/Catalogos/GestReviews.java \
		Model/Pair/*.java \
		Model/Query/Query_Interface.java \
		Model/Query/*.java \
		Model/Parser.java \
		View/Viewer.java \
		Controller/Information.java \
		Controller/Controller.java \
		Controller/GestReviewsAppMVC.java

default:  $@ classes

classes: $(CLASSES:.java=.class)

clean:
	find . -name "*.class" -type f -delete
	
run:
	java Controller.GestReviewsAppMVC
build:
	make
	make run
