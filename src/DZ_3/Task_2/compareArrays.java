package DZ_3.Task_2;

public class compareArrays {
    public static void main(String[] args) {
//        Double[] arr1 = {2.1, 2.2, 2.3, 2.4};
//        Double[] arr2 = {2.1, 2.2, 2.3};

        Double[] arr1 = {2.1, 2.2, 2.3, 2.4};
        Double[] arr2 = {2.1, 2.2, 2.3, 2.4};

        boolean result = compareArrays(arr1, arr2);
        System.out.println(result);
    }

    public static <T> boolean compareArrays(T[] arr1, T[] arr2){
        if (arr1.length == arr2.length){
            for (int i = 0; i< arr1.length;i++){
                if (!arr1[i].equals(arr2[i])){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
