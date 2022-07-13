//Isaiah Herr (herrx080, 5333708) and See Long Thao (thao0478@umn.edu, 5360172) Project 3

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Hash test1 = new Hash();

		//test1.createHash(); //Used for part 2 of the project
		//test1.display();
		test1.distribute(); //Used for part 4 of the project
		//System.out.println("============");
		test1.display();

		Hash test2 = new Hash(79);
		//test2.createHash();
		//test2.specificHash(); //This is used for Part 5 of the project.
		//test2.display();
    }


	}
