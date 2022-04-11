import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dayushman.android_treemap_custom.MapLayoutView
import com.dayushman.android_treemap_custom.sample.SampleData
import com.dayushman.android_treemap_custom.sample.SamplePopulationData

class TreemapFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MapLayoutView(this.activity,SampleData().getTreeModel())
    }
}