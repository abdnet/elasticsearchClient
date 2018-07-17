package imnet.ft.indexing.IndexBuilder;

import org.elasticsearch.common.xcontent.XContentBuilder;

import imnet.ft.indexing.Index.ImnetAnalysis;
import imnet.ft.indexing.Index.ImnetSettings;

public class SettingsBuilder {

		private int shards=1;
		private int replicas=5;
		private ImnetAnalysis analysis;
		
		
		
	
	
		
		
		public SettingsBuilder setShards(int shards) {
			this.shards = shards;
			return this;
		}

		public SettingsBuilder setReplicas(int replicas) {
			this.replicas = replicas;
			return this;
		}
		public SettingsBuilder setAnalysis(ImnetAnalysis analysis) {
			this.analysis = analysis;
			return this;
		}
		@Override
		public String toString() {
			return "SettingsBuilder [shards=" + shards + ", replicas=" + replicas + ", analysis=" + analysis + "]";
		}
		
		public ImnetSettings build() {
			return new ImnetSettings(shards, replicas, analysis);
		}
		
		
		
		
}
