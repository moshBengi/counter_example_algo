import java.util.*;

public class part {
    static int num_of_parts = 11;
    float start;
    float end;
    int common;
    part(float a, float b){
        start = Math.min(b, a);
        end = Math.max(b, a);
        common = 0;
    }
    public static boolean isCommon(part a, part b){
        return a.start <= b.end && a.start >= b.start || b.start <= a.end && b.start >= a.start;
    }
    public static List<part> counter(){
        List<part> partList = new LinkedList<>();
        partList.add(new part(0,1));
        partList.add(new part(0,2));
        partList.add(new part(0,3));
        partList.add(new part(0,4));
        partList.add(new part(1.5f,4.9f));
        partList.add(new part(4.5f,5.5f));
        partList.add(new part(5.1f,8.5f));
        partList.add(new part(6,10));
        partList.add(new part(7,10));
        partList.add(new part(8,10));
        partList.add(new part(9,10));
        return partList;
    }
    public static void main(String[] args) {
        Random rand = new Random();
//        List<part> parts = counter();
//        int A = first_algo(parts);
//        int B = optimal_algo(parts);

        for (int j = 0; j < 200000000; j++)
        {
            if (j % 2000000 == 0){
                System.out.println(j / 2000000 + "%");
            }
//            System.out.println(j);
            List<part> parts = new LinkedList<>();
            for (int i = 0; i < num_of_parts; i++) {
                parts.add(new part(rand.nextFloat(), rand.nextFloat()));
            }
            int A = first_algo(parts);
            int B = optimal_algo(parts);
            if ((A != B)) {
                System.out.println("not optimal:" + A);
                System.out.println("optimal:" + B);
                for (part part: parts){
                    System.out.println("["+part.start+","+part.end+"]");
                }
                return;
            }
//            System.out.println(j +":"+ A+","+ B);
        }
    }

    private static int optimal_algo(List<part> parts) {
        if (parts.size() == 0){
            return 0;
        }
        class comp implements Comparator<part>{
            @Override
            public int compare(part o1, part o2) {
                return Float.compare(o1.end, o2.end);
            }
        }
        parts.sort(new comp());
        List<part> new_parts = new LinkedList<>(parts);
        part used = new_parts.get(0);
        int i = 0;
        while (i < new_parts.size()){
            if (isCommon(used, new_parts.get(i))){
                new_parts.remove(i);
            }
            else {
                i++;
            }
        }
        return 1 + optimal_algo(new_parts);
    }

    private static int first_algo(List<part> parts) {
        if (parts.size() == 0){
            return 0;
        }
        for (part part:parts){
            part.common = 0;
            for (part part1:parts){
                if (part != part1){
                    if (isCommon(part, part1)){
                        part.common += 1;
                    }
                }
            }
        }
        class compe implements Comparator<part>{
            @Override
            public int compare(part o1, part o2) {
                return Integer.compare(o1.common, o2.common);
            }
        }
        parts.sort(new compe());
        List<part> new_parts = new LinkedList<>(parts);

        int min_common = new_parts.get(0).common;
        int commoms = 0;
        while (new_parts.get(commoms).common == min_common){
            commoms ++;
            if (commoms == new_parts.size()){
                break;
            }
        }
        int[] m = new int[commoms];
        for (int j = 0 ; j< commoms; j ++){
            part used = new_parts.get(j);
            List<part> anotherParts = new LinkedList<>(new_parts);
            int i = 0;
            while (i < anotherParts.size()){
                if (isCommon(used, anotherParts.get(i))){
                    anotherParts.remove(i);
                }
                else {
                    i++;
                }
            }
            m[j] = 1 + first_algo(anotherParts);
        }
        int max = 0;
        for (int j : m) {
            if (max < j) {
                max = j;
            }
        }
        return max;
    }
}
