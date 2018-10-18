package abhishek.com.sortingexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<ServiceCentre> serviceCentres = new ArrayList<>();
        serviceCentres.add(new ServiceCentre(1, "One", 4000));
        serviceCentres.add(new ServiceCentre(1, "Two", 1000));
        serviceCentres.add(new ServiceCentre(1, "Three", 55000));
        serviceCentres.add(new ServiceCentre(1, "Four", 22000));
        sorting(serviceCentres);

    }


    private void sorting(List<ServiceCentre> myList) {
        Collections.sort(myList, new Comparator<ServiceCentre>() {
            public int compare(ServiceCentre obj1, ServiceCentre obj2) {
                // ## Ascending order
                return obj2.getName().compareToIgnoreCase(obj1.getName()); // To compare string values
//                 return Double.valueOf(obj2.getPrice()).compareTo(obj1.getPrice()); // To compare integer values

                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                // return Integer.valueOf(obj2.empId).compareTo(obj1.empId); // To compare integer values
            }
        });

        for (int i = 0; i < myList.size(); i++) {
            Log.e("BaaaTAG", "sorting: " + myList.get(i).getName() + "");
        }
    }
}
