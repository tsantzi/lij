rm ../src/lij/parser/*.java

javacc -OUTPUT_DIRECTORY:../src/lij/parser/ lcc.jj
