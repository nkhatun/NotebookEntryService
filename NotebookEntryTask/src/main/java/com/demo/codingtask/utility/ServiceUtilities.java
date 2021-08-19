package com.demo.codingtask.utility;

public class ServiceUtilities {
	public static int getLavenshteinDistance(String searchWord, String compareWord) {
		int searchWordLength = searchWord.length();
		int compareWordLength = compareWord.length();
		int matrixArr[][] = new int[searchWordLength + 1][compareWordLength
				+ 1];
		int distance = 0;
		if (searchWord.isEmpty()) {
			return compareWordLength;
		}
		if (compareWord.isEmpty()) {
			return searchWordLength;
		}

		int searchWordArr[] = new int[searchWordLength + 1];
		int compareWordArr[] = new int[compareWordLength + 1];
		for (int i = 1; i < searchWordArr.length; i++) {
			searchWordArr[i] = Character.toLowerCase(searchWord.charAt(i - 1));
		}
		for (int i = 1; i < compareWordArr.length; i++) {
			compareWordArr[i] = Character
					.toLowerCase(compareWord.charAt(i - 1));
		}

		for (int i = 0; i <= searchWordLength; i++) {
			matrixArr[i][0] = i;
		}

		for (int j = 0; j <= compareWordLength; j++) {
			matrixArr[0][j] = j;
		}

		for (int i = 1; i <= searchWordLength; i++) {

			for (int j = 1; j <= compareWordLength; j++) {
				if (searchWordArr[i] == compareWordArr[j]) {
					distance = 0;
				} else {
					distance = 1;
				}
				matrixArr[i][j] = Math.min(matrixArr[i - 1][j] + 1,
						Math.min((matrixArr[i][j - 1] + 1),
								matrixArr[i - 1][j - 1] + distance));

				if (i > 1 && j > 1
						&& (searchWordArr[i] == compareWordArr[j - 1])
						&& (searchWordArr[i - 1] == compareWordArr[j])) {
					matrixArr[i][j] = Math.min(matrixArr[i][j],
							matrixArr[i - 2][j - 2] + 1);
				}

			}
		}
		return matrixArr[searchWordLength][compareWordLength];
	}
}
