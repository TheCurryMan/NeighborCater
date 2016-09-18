//
//  AddKitchenViewController.swift
//  NeighborCater
//
//  Created by Avinash Jain on 9/17/16.
//  Copyright © 2016 Avinash Jain. All rights reserved.
//

import UIKit
import Firebase
import FirebaseAuth

class AddKitchenViewController: UIViewController, UITextFieldDelegate {
    
    @IBOutlet weak var location: UITextField!
    @IBOutlet weak var kitchenName: UITextField!
    
    
    var longitude : Double = 0.0
    var latitude : Double = 0.0
    var kitchenKey = String()
    let gpaViewController = GooglePlacesAutocomplete(
        apiKey: "AIzaSyBbdYNbqJZm0gWCjBT7wY3iG7fOigkOTNc",
        placeType: .Address
    )
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        gpaViewController.placeDelegate = self
        
    }
    
    func textFieldDidBeginEditing(textField: UITextField) {
        if textField == location {
            presentViewController(gpaViewController, animated: true, completion: nil)
            
        }
    }
    
    @IBAction func complete(sender: AnyObject) {
        let ref = FIRDatabase.database().reference()
        let key = ref.child("kitchens").childByAutoId().key
        let kitchen : [String: AnyObject] = ["user": (FIRAuth.auth()?.currentUser!.uid)!,
                    "kitchenName": kitchenName.text!,
                    "address": location.text!,
                    "latitude": latitude,
                    "longitude": longitude]
        let childUpdates : [NSObject: AnyObject] = ["/kitchens/\(key)":kitchen as! AnyObject]
        ref.updateChildValues(childUpdates)
        kitchenKey = key
        performSegueWithIdentifier("kitchen", sender: self)
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "kitchen" {
            var vc:KitchenHomeViewController = segue.destinationViewController as! KitchenHomeViewController
            if let key = kitchenKey as? String {
                vc.kitchenUID = key
            }
            
        }
    }
    
}

extension AddKitchenViewController: GooglePlacesAutocompleteDelegate {
    func placeSelected(place: Place) {
        place.getDetails { details in
            print(details)
            self.latitude = details.latitude
            self.longitude = details.longitude
            self.location.text = place.description
        }
        placeViewClosed()
    }
    
    func placeViewClosed() {
        dismissViewControllerAnimated(true, completion: nil)
    }
}
