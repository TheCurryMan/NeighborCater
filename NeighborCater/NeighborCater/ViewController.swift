//
//  ViewController.swift
//  NeighborCater
//
//  Created by Avinash Jain on 9/17/16.
//  Copyright © 2016 Avinash Jain. All rights reserved.
//

import UIKit
import Firebase

class ViewController: UIViewController {

    @IBAction func goKitchen(sender: AnyObject) {
        let postRef = FIRDatabase.database().reference().child("kitchens")
        let refHandle = postRef.observeEventType(FIRDataEventType.Value, withBlock: { (snapshot) in
            for i in snapshot.children.allObjects as! [FIRDataSnapshot] {
                if let user = i.value!["user"] as? String {
                if user == (FIRAuth.auth()?.currentUser?.uid)! {
                    self.performSegueWithIdentifier("makeFood", sender: self)
                }
                }
            }
            self.performSegueWithIdentifier("makeKitchen", sender: self)
            // ...
        })
        
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

