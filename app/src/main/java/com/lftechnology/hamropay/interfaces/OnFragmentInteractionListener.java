package com.lftechnology.hamropay.interfaces;

import com.lftechnology.hamropay.model.UserInfo;

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 */
public interface OnFragmentInteractionListener {
    void onFragmentInteraction(UserInfo userInfo, String tag);
}
