simple:
	javac -cp robocode.jar ParallelBot.java

deploy:
	cp ParallelBot.class ~/robocode/robots/ccc/
	cp ParallelBot.properties ~/robocode/robots/ccc/

makejar:
	jar ParallelBot.jar ParallelBot.class
