import java.util.*;
import net.mindview.util.*;

public class SimpleHashMapArr<K, V> extends AbstractMap<K, V> {
	// Choose a prime number for the hash table
	// size, to achieve a uniform distribution:
	static final int SIZE = 997;
	// You can't have a physical array of generics,
	// but you can upcast to one:
	@SuppressWarnings("unchecked")
	ArrayList<MapEntry<K,V>>[] buckets = new ArrayList[SIZE];

	public V put(K key, V value) {
		V oldValue = null;
		int index = Math.abs(key.hashCode()) % SIZE;
		if (buckets[index] == null)
			buckets[index] = new ArrayList<MapEntry<K, V>>();
		ArrayList<MapEntry<K, V>> bucket = buckets[index];
		MapEntry<K, V> pair = new MapEntry<K, V>(key, value);
		boolean found = false;
		ListIterator<MapEntry<K, V>> it = bucket.listIterator();
		while (it.hasNext()) {
			MapEntry<K, V> iPair = it.next();
			if (iPair.getKey().equals(key)) {
				oldValue = iPair.getValue();
				it.set(pair); // Replace old with new
				found = true;
				break;
			}
		}
		if (!found)
			buckets[index].add(pair);
		return oldValue;
	}

	public V get(Object key) {
		int index = Math.abs(key.hashCode()) % SIZE;
		if (buckets[index] == null)
			return null;
		for (MapEntry<K, V> iPair : buckets[index])
			if (iPair.getKey().equals(key))
				return iPair.getValue();
		return null;
	}

	public Set<Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> set = new HashSet<Map.Entry<K, V>>();
		for (ArrayList<MapEntry<K, V>> bucket : buckets) {
			if (bucket == null)
				continue;
			for (MapEntry<K, V> mpair : bucket)
				set.add(mpair);
		}
		return set;
	}

	public static void main(String[] args) {
		SimpleHashMapArr<String, String> m = new SimpleHashMapArr<String, String>();
		m.putAll(Countries.capitals(25));
		System.out.println(m);
		System.out.println(m.get("ERITREA"));
		System.out.println(m.entrySet());
	}
}

/* Output:
{CAPE VERDE=Praia, ANGOLA=Luanda, ETHIOPIA=Addis Ababa, BENIN=Porto-Novo, CONGO=Brazzaville, LESOTHO=Maseru, CENTRAL AFRICAN REPUBLIC=Bangui, EQUATORIAL GUINEA=Malabo, ERITREA=Asmara, COMOROS=Moroni, BURKINA FASO=Ouagadougou, GABON=Libreville, THE GAMBIA=Banjul, GUINEA=Conakry, EGYPT=Cairo, BURUNDI=Bujumbura, ALGERIA=Algiers, CAMEROON=Yaounde, GHANA=Accra, KENYA=Nairobi, COTE D'IVOIR (IVORY COAST)=Yamoussoukro, BISSAU=Bissau, DJIBOUTI=Dijibouti, CHAD=N'djamena, BOTSWANA=Gaberone}
Asmara
[CAPE VERDE=Praia, ANGOLA=Luanda, ETHIOPIA=Addis Ababa, BENIN=Porto-Novo, CONGO=Brazzaville, LESOTHO=Maseru, CENTRAL AFRICAN REPUBLIC=Bangui, EQUATORIAL GUINEA=Malabo, ERITREA=Asmara, COMOROS=Moroni, BURKINA FASO=Ouagadougou, GABON=Libreville, THE GAMBIA=Banjul, GUINEA=Conakry, EGYPT=Cairo, BURUNDI=Bujumbura, ALGERIA=Algiers, CAMEROON=Yaounde, GHANA=Accra, KENYA=Nairobi, COTE D'IVOIR (IVORY COAST)=Yamoussoukro, BISSAU=Bissau, DJIBOUTI=Dijibouti, CHAD=N'djamena, BOTSWANA=Gaberone]
*/