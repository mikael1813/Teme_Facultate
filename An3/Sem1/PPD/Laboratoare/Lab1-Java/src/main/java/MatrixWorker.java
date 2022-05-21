import java.util.ArrayList;
import java.util.List;

public class MatrixWorker {
    public List<List<Integer>> expand(List<List<Integer>> matrix, int nr) {
        for (int i = 0; i < nr; i++) {
            List<Integer> firstList = new ArrayList<>();
            List<Integer> lastList = new ArrayList<>();
            for (int j = 0; j < matrix.get(0).size(); j++) {
                firstList.add((Integer) matrix.get(0).get(j));
                lastList.add((Integer) matrix.get(matrix.size() - 1).get(j));
            }
            //firstList = matrix.get(0);
            firstList.add(0, firstList.get(0));
            lastList.add(0, lastList.get(0));
            firstList.add(firstList.size() - 1, firstList.get(firstList.size() - 1));
            lastList.add(lastList.size() - 1, lastList.get(lastList.size() - 1));
            matrix.add(0, firstList);
            matrix.add(matrix.size(), lastList);
            for (int j = 1; j < matrix.size() - 1; j++) {
                matrix.get(j).add(0, matrix.get(j).get(0));
                matrix.get(j).add(matrix.get(j).size() - 1, matrix.get(j).get(matrix.get(j).size() - 1));
            }

        }


        return null;
    }

    public void uniteByRows(List<List<Integer>> matrix1, List<List<Integer>> matrix2) {
        for (int i = 0; i < matrix2.size(); i++) {
            matrix1.add(matrix2.get(i));
        }
    }

    public static void main(String[] args) {

    }

    public void uniteByColumns(List<List<Integer>> endMatrix, List<List<Integer>> temporaryMatrix) {
        for (int i = 0; i < endMatrix.size(); i++) {
            endMatrix.get(i).addAll(temporaryMatrix.get(i));
        }
    }
}
