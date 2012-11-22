package sample.edukaquiz;

public class BoxShuffle {
	
	public static void shuffle(Object[] box){
		swap(box);
	}
	
	public static void shuffle(int[] box){

		for(int i=0;i<box.length;i++){
			int dst = (int)Math.floor(Math.random()*(i+1));
			int j = box[i];
			box[i] = box[dst];
			box[dst] = j;
		}
	}
	
	private static void swap(Object[] o){
		for(int i=0;i<o.length;i++){
			int dst = (int)Math.floor(Math.random()*(i+1));
			Object j = o[i];
			o[i] = o[dst];
			o[dst] = j;
		}
	}
	
	private BoxShuffle(){
		
	}

}
