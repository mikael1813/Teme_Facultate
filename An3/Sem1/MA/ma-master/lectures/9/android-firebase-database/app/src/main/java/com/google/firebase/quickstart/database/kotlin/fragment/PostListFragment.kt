package com.google.firebase.quickstart.database.kotlin.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.quickstart.database.R
import com.google.firebase.quickstart.database.kotlin.PostDetailActivity
import com.google.firebase.quickstart.database.kotlin.logd
import com.google.firebase.quickstart.database.kotlin.models.Post
import com.google.firebase.quickstart.database.kotlin.viewholder.PostViewHolder
import kotlinx.android.synthetic.main.fragment_all_posts.view.*

abstract class PostListFragment : Fragment() {

  private lateinit var database: DatabaseReference

  private lateinit var recycler: RecyclerView
  private lateinit var manager: LinearLayoutManager
  private var adapter: FirebaseRecyclerAdapter<Post, PostViewHolder>? = null

  val uid: String
    get() = FirebaseAuth.getInstance().currentUser!!.uid

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    val rootView = inflater.inflate(R.layout.fragment_all_posts, container, false)

    database = FirebaseDatabase.getInstance().reference

    recycler = rootView.findViewById(R.id.messagesList)
    rootView.messagesList.setHasFixedSize(true)

    return rootView
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    // Set up Layout Manager, reverse layout
    manager = LinearLayoutManager(activity)
    manager.reverseLayout = true
    manager.stackFromEnd = true
    recycler.layoutManager = manager

    // Set up FirebaseRecyclerAdapter with the Query
    val postsQuery = getQuery(database)

    val options = FirebaseRecyclerOptions.Builder<Post>()
        .setQuery(postsQuery, Post::class.java)
        .build()

    adapter = object : FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {

      override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PostViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return PostViewHolder(inflater.inflate(R.layout.item_post, viewGroup, false))
      }

      override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int, model: Post) {
        val postRef = getRef(position)

        // Set click listener for the whole post view
        val postKey = postRef.key
        viewHolder.itemView.setOnClickListener {
          // Launch PostDetailActivity
          val intent = Intent(activity, PostDetailActivity::class.java)
          intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postKey)
          startActivity(intent)
        }

        // Determine if the current user has liked this post and set UI accordingly
        viewHolder.setLikedState(model.stars.containsKey(uid))

        // Bind Post to ViewHolder, setting OnClickListener for the star button
        viewHolder.bindToPost(model) {
          // Need to write to both places the post is stored
          val globalPostRef = database.child("posts").child(postRef.key!!)
          val userPostRef = database.child("user-posts").child(model.uid!!).child(postRef.key!!)

          // Run two transactions
          onStarClicked(globalPostRef)
          onStarClicked(userPostRef)
        }
      }
    }
    recycler.adapter = adapter
  }

  private fun onStarClicked(postRef: DatabaseReference) {
    postRef.runTransaction(object : Transaction.Handler {
      override fun doTransaction(mutableData: MutableData): Transaction.Result {
        val p = mutableData.getValue(Post::class.java)
            ?: return Transaction.success(mutableData)

        if (p.stars.containsKey(uid)) {
          // Unstar the post and remove self from stars
          p.starCount = p.starCount - 1
          p.stars.remove(uid)
        } else {
          // Star the post and add self to stars
          p.starCount = p.starCount + 1
          p.stars[uid] = true
        }

        // Set value and report transaction success
        mutableData.value = p
        return Transaction.success(mutableData)
      }

      override fun onComplete(
          databaseError: DatabaseError?,
          b: Boolean,
          dataSnapshot: DataSnapshot?
      ) {
        // Transaction completed
        logd("postTransaction:onComplete: $databaseError")
      }
    })
  }

  override fun onStart() {
    super.onStart()
    adapter?.startListening()
  }

  override fun onStop() {
    super.onStop()
    adapter?.stopListening()
  }

  abstract fun getQuery(databaseReference: DatabaseReference): Query
}
